/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

/**
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
AUI.add(
	'liferay-ratings',
	(A) => {
		const Lang = A.Lang;

		const CSS_ICON_STAR = 'icon-star-on';

		const CSS_ICON_STAR_EMPTY = 'icon-star-off';

		const EMPTY_FN = Lang.emptyFn;

		const EVENT_INTERACTIONS_RENDER = ['focus', 'mousemove', 'touchstart'];

		const SELECTOR_RATING_ELEMENT = '.rating-element';

		const STR_INITIAL_FOCUS = 'initialFocus';

		const STR_NAMESPACE = 'namespace';

		const STR_SIZE = 'size';

		const STR_URI = 'uri';

		const STR_YOUR_SCORE = 'yourScore';

		const TPL_LABEL_SCORE = '{description} ({totalEntries} {voteLabel})';
		const TPL_LABEL_SCORE_STACKED = '({totalEntries} {voteLabel})';

		const buffer = [];

		const Ratings = A.Component.create({
			_INSTANCES: {},

			_registerRating(config) {
				const instance = this;

				let ratings = null;

				if (config.type === 'like') {
					ratings = Liferay.Ratings.LikeRating;
				}
				else if (
					config.type === 'stars' ||
					config.type === 'stacked-stars'
				) {
					ratings = Liferay.Ratings.StarRating;
				}
				else if (config.type === 'thumbs') {
					ratings = Liferay.Ratings.ThumbRating;
				}

				let ratingInstance = null;

				if (ratings && document.getElementById(config.containerId)) {
					ratingInstance = new ratings(config);

					instance._INSTANCES[
						config.id || config.namespace
					] = ratingInstance;
				}

				return ratingInstance;
			},

			_registerTask: A.debounce(() => {
				buffer.forEach((item) => {
					const handle = item.container.on(
						EVENT_INTERACTIONS_RENDER,
						(event) => {
							handle.detach();

							const config = item.config;

							config.initialFocus = event.type === 'focus';

							Ratings._registerRating(config);
						}
					);
				});

				buffer.length = 0;
			}, 100),

			_thumbScoreMap: {
				'-1': -1,
				'down': 0,
				'up': 1,
			},

			ATTRS: {
				averageScore: {},

				className: {},

				classPK: {},

				namespace: {},

				round: {},

				size: {},

				totalEntries: {},

				totalScore: {},

				type: {},

				uri: {},

				yourScore: {
					getter(value) {
						const instance = this;

						let yourScore = value;

						if (
							(instance.get('type') === 'stars' ||
								instance.get('type') === 'stacked-stars') &&
							yourScore === -1.0
						) {
							yourScore = 0;
						}

						return yourScore;
					},
				},
			},

			EXTENDS: A.Base,

			prototype: {
				_bindRatings() {
					const instance = this;

					instance.ratings.after(
						'itemSelect',
						instance._itemSelect,
						instance
					);
				},

				_convertToIndex(score) {
					let scoreIndex = -1;

					if (score === 1.0) {
						scoreIndex = 0;
					}
					else if (score === 0.0) {
						scoreIndex = 1;
					}

					return scoreIndex;
				},

				_fixScore(score) {
					let prefix = '';

					if (score > 0) {
						prefix = '+';
					}

					return prefix + score;
				},

				_getLabel(description, totalEntries) {
					const instance = this;

					let tplLabel = '';

					if (instance.get('type') === 'stacked-stars') {
						tplLabel = TPL_LABEL_SCORE_STACKED;
					}
					else {
						tplLabel = TPL_LABEL_SCORE;
					}

					let voteLabel = '';

					if (totalEntries === 1) {
						voteLabel = Liferay.Language.get('vote');
					}
					else {
						voteLabel = Liferay.Language.get('votes');
					}

					return Lang.sub(tplLabel, {
						description,
						totalEntries,
						voteLabel,
					});
				},

				_itemSelect: EMPTY_FN,

				_renderRatings: EMPTY_FN,

				_sendVoteRequest(url, score, callback) {
					const instance = this;

					Liferay.fire('ratings:vote', {
						className: instance.get('className'),
						classPK: instance.get('classPK'),
						ratingType: instance.get('type'),
						score,
					});

					const data = {
						className: instance.get('className'),
						classPK: instance.get('classPK'),
						p_auth: Liferay.authToken,
						p_l_id: themeDisplay.getPlid(),
						score,
					};

					Liferay.Util.fetch(url, {
						body: Liferay.Util.objectToFormData(data),
						method: 'POST',
					})
						.then((response) => response.json())
						.then((response) => callback.call(instance, response));
				},

				_showScoreTooltip(event) {
					const instance = this;

					let message = '';

					const stars = instance._ratingScoreNode
						.all('.icon-star-on')
						.size();

					if (stars === 1) {
						message = Liferay.Language.get('star');
					}
					else {
						message = Liferay.Language.get('stars');
					}

					const currentTarget = event.currentTarget.getDOM();

					currentTarget.setAttribute('title', stars + ' ' + message);
				},

				_updateAverageScoreText(averageScore) {
					const instance = this;

					const firstNode = instance._ratingScoreNode.one(
						SELECTOR_RATING_ELEMENT
					);

					if (firstNode) {
						let message = '';

						if (averageScore === 1.0) {
							message = Liferay.Language.get(
								'the-average-rating-is-x-star-out-of-x'
							);
						}
						else {
							message = Liferay.Language.get(
								'the-average-rating-is-x-stars-out-of-x'
							);
						}

						const averageRatingText = Lang.sub(message, [
							averageScore,
							instance.get(STR_SIZE),
						]);

						firstNode.attr('title', averageRatingText);
					}
				},

				_updateScoreText(score) {
					const instance = this;

					const nodes = instance._ratingStarNode.all(
						'.rating-element'
					);

					nodes.each((node, i) => {
						let ratingMessage = '';
						let ratingScore = '';

						if (score === i + 1) {
							ratingMessage =
								i === 0
									? Liferay.Language.get(
											'you-have-rated-this-x-star-out-of-x'
									  )
									: Liferay.Language.get(
											'you-have-rated-this-x-stars-out-of-x'
									  );
							ratingScore = score;
						}
						else {
							ratingMessage =
								i === 0
									? Liferay.Language.get(
											'rate-this-x-star-out-of-x'
									  )
									: Liferay.Language.get(
											'rate-this-x-stars-out-of-x'
									  );
							ratingScore = i + 1;
						}

						node.attr(
							'title',
							Lang.sub(ratingMessage, [
								ratingScore,
								instance.get(STR_SIZE),
							])
						);
					});
				},

				initializer() {
					const instance = this;

					instance._renderRatings();
				},
			},

			register(config) {
				const instance = this;

				const containerId = config.containerId;

				const container =
					containerId && document.getElementById(config.containerId);

				if (container) {
					buffer.push({
						config,
						container: A.one(container),
					});

					instance._registerTask();
				}
				else {
					instance._registerRating(config);
				}
			},
		});

		const StarRating = A.Component.create({
			ATTRS: {
				initialFocus: {
					validator: Lang.isBoolean,
				},
			},

			EXTENDS: Ratings,

			prototype: {
				_itemSelect() {
					const instance = this;

					const score =
						(instance.ratings.get('selectedIndex') + 1) /
						instance.get(STR_SIZE);
					const uri = instance.get(STR_URI);

					instance._sendVoteRequest(
						uri,
						score,
						instance._saveCallback
					);
				},

				_renderRatings() {
					const instance = this;

					const namespace = instance.get(STR_NAMESPACE);

					instance._ratingScoreNode = A.one(
						'#' + namespace + 'ratingScoreContent'
					);
					instance._ratingStarNode = A.one(
						'#' + namespace + 'ratingStarContent'
					);

					if (themeDisplay.isSignedIn()) {
						const yourScore =
							instance.get(STR_YOUR_SCORE) *
							instance.get(STR_SIZE);

						instance.ratings = new A.StarRating({
							boundingBox: '#' + namespace + 'ratingStar',
							canReset: false,
							cssClasses: {
								element: CSS_ICON_STAR_EMPTY,
								hover: CSS_ICON_STAR,
								off: CSS_ICON_STAR_EMPTY,
								on: CSS_ICON_STAR,
							},
							defaultSelected: yourScore,
							srcNode: '#' + namespace + 'ratingStarContent',
						}).render();

						if (instance.get(STR_INITIAL_FOCUS)) {
							instance.ratings.get('elements').item(0).focus();
						}

						instance._bindRatings();
					}

					instance._ratingScoreNode.on(
						'mouseenter',
						instance._showScoreTooltip,
						instance
					);
				},

				_saveCallback(response) {
					const instance = this;

					const description = Liferay.Language.get('average');

					const averageScore =
						response.averageScore * instance.get(STR_SIZE);

					const score = response.score * instance.get(STR_SIZE);

					const label = instance._getLabel(
						description,
						response.totalEntries
					);

					const formattedAverageScore = averageScore.toFixed(1);

					const averageIndex = instance.get('round')
						? Math.round(formattedAverageScore)
						: Math.floor(formattedAverageScore);

					const ratingScore = instance._ratingScoreNode;

					ratingScore.one('.rating-label').html(label);

					ratingScore
						.all(SELECTOR_RATING_ELEMENT)
						.each((item, index) => {
							let fromCssClass = CSS_ICON_STAR;
							let toCssClass = CSS_ICON_STAR_EMPTY;

							if (index < averageIndex) {
								fromCssClass = CSS_ICON_STAR_EMPTY;
								toCssClass = CSS_ICON_STAR;
							}

							item.replaceClass(fromCssClass, toCssClass);
						});

					instance._updateAverageScoreText(formattedAverageScore);
					instance._updateScoreText(score);
				},
			},
		});

		const ThumbRating = A.Component.create({
			ATTRS: {
				initialFocus: {
					validator: Lang.isBoolean,
				},
			},

			EXTENDS: Ratings,

			prototype: {
				_createRating() {
					const instance = this;

					const namespace = instance.get(STR_NAMESPACE);

					instance.ratings = new A.ThumbRating({
						boundingBox: '#' + namespace + 'ratingThumb',
						cssClasses: {
							down: '',
							element: 'rating-off',
							hover: 'rating-on',
							off: 'rating-off',
							on: 'rating-on',
							up: '',
						},
						srcNode: '#' + namespace + 'ratingThumbContent',
					}).render();
				},

				_getThumbScores(entries, score) {
					const positiveVotes = Math.floor(score);

					const negativeVotes = entries - positiveVotes;

					return {
						negativeVotes,
						positiveVotes,
					};
				},

				_itemSelect() {
					const instance = this;

					const uri = instance.get(STR_URI);
					const value = instance.ratings.get('value');

					const score = Liferay.Ratings._thumbScoreMap[value];

					instance._sendVoteRequest(
						uri,
						score,
						instance._saveCallback
					);
				},

				_renderRatings() {
					const instance = this;

					if (themeDisplay.isSignedIn()) {
						const yourScore = instance.get(STR_YOUR_SCORE);

						const yourScoreIndex = instance._convertToIndex(
							yourScore
						);

						const namespace = instance.get(STR_NAMESPACE);

						instance._createRating();

						if (instance.get(STR_INITIAL_FOCUS)) {
							A.one('#' + namespace + 'ratingThumb a').focus();
						}

						instance._bindRatings();

						instance.ratings.select(yourScoreIndex);
					}
				},

				_saveCallback(response) {
					const instance = this;

					const thumbScore = instance._getThumbScores(
						response.totalEntries,
						response.totalScore
					);

					instance._updateScores(thumbScore);
				},

				_updateScores(thumbScore) {
					const instance = this;

					const ratings = instance.ratings;

					const cssClasses = ratings.get('cssClasses');
					const elements = ratings.get('elements');

					const ratingThumbDown = elements.item(1);
					const ratingThumbUp = elements.item(0);

					if (
						isNaN(thumbScore.negativeVotes) &&
						isNaN(thumbScore.positiveVotes)
					) {
						ratingThumbDown.attr(
							'title',
							Liferay.Language.get(
								'you-must-be-signed-in-to-rate'
							)
						);
						ratingThumbUp.attr(
							'title',
							Liferay.Language.get(
								'you-must-be-signed-in-to-rate'
							)
						);

						ratingThumbDown.addClass(cssClasses.off);
						ratingThumbUp.addClass(cssClasses.off);

						ratingThumbDown.removeClass(cssClasses.on);
						ratingThumbUp.removeClass(cssClasses.on);

						ratings.set('disabled', true);
					}
					else {
						const cssClassesOn = cssClasses.on;

						let ratingThumbDownCssClassOn = false;

						if (ratingThumbDown) {
							ratingThumbDownCssClassOn = ratingThumbDown.hasClass(
								cssClassesOn
							);
						}

						let ratingThumbUpCssClassOn = false;

						if (ratingThumbUp) {
							ratingThumbUpCssClassOn = ratingThumbUp.hasClass(
								cssClassesOn
							);
						}

						let thumbDownMessage = '';
						let thumbUpMessage = '';

						if (ratingThumbDown) {
							if (ratingThumbDownCssClassOn) {
								thumbDownMessage = Liferay.Language.get(
									'you-have-rated-this-as-bad'
								);
							}
							else {
								thumbDownMessage = Liferay.Language.get(
									'rate-this-as-bad'
								);
							}

							ratingThumbDown.attr('title', thumbDownMessage);

							ratingThumbDown
								.one('.votes')
								.html(thumbScore.negativeVotes);
						}

						if (ratingThumbDown && ratingThumbUpCssClassOn) {
							thumbUpMessage = Liferay.Language.get(
								'you-have-rated-this-as-good'
							);
						}
						else if (
							ratingThumbDown &&
							!ratingThumbUpCssClassOn
						) {
							thumbUpMessage = Liferay.Language.get(
								'rate-this-as-good'
							);
						}
						else if (
							!ratingThumbDown &&
							ratingThumbUpCssClassOn
						) {
							thumbUpMessage = Liferay.Language.get(
								'unlike-this'
							);
						}
						else if (
							!ratingThumbDown &&
							!ratingThumbUpCssClassOn
						) {
							thumbUpMessage = Liferay.Language.get('like-this');
						}

						if (ratingThumbUp) {
							ratingThumbUp.attr('title', thumbUpMessage);

							ratingThumbUp
								.one('.votes')
								.html(thumbScore.positiveVotes);
						}
					}
				},
			},
		});

		const LikeRatingImpl = A.Component.create({
			EXTENDS: A.ThumbRating,

			NAME: 'LikeRatingImpl',

			prototype: {
				renderUI() {
					const instance = this;

					const cssClasses = instance.get('cssClasses');

					A.ThumbRating.superclass.renderUI.apply(this, arguments);

					const elements = instance.get('elements');

					elements.addClass(cssClasses.off);
					elements.item(0).addClass(cssClasses.up);
				},
			},
		});

		const LikeRating = A.Component.create({
			EXTENDS: ThumbRating,

			NAME: 'LikeRating',

			prototype: {
				_createRating() {
					const instance = this;

					const namespace = instance.get(STR_NAMESPACE);

					instance.ratings = new LikeRatingImpl({
						boundingBox: '#' + namespace + 'ratingLike',
						cssClasses: {
							down: '',
							element: 'rating-off',
							hover: 'rating-on',
							off: 'rating-off',
							on: 'rating-on',
							up: '',
						},
						srcNode: '#' + namespace + 'ratingLikeContent',
					}).render();
				},

				_getThumbScores(entries) {
					return {
						positiveVotes: entries,
					};
				},
			},
		});

		Ratings.LikeRating = LikeRating;
		Ratings.StarRating = StarRating;
		Ratings.ThumbRating = ThumbRating;

		Liferay.Ratings = Ratings;
	},
	'',
	{
		requires: ['aui-rating'],
	}
);
