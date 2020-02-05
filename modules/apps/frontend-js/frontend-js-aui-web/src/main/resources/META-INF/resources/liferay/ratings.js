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

AUI.add(
	'liferay-ratings',
	A => {
		var Lang = A.Lang;

		var CSS_ICON_STAR = 'icon-star-on';

		var CSS_ICON_STAR_EMPTY = 'icon-star-off';

		var EMPTY_FN = Lang.emptyFn;

		var EVENT_INTERACTIONS_RENDER = ['focus', 'mousemove', 'touchstart'];

		var SELECTOR_RATING_ELEMENT = '.rating-element';

		var STR_INITIAL_FOCUS = 'initialFocus';

		var STR_NAMESPACE = 'namespace';

		var STR_SIZE = 'size';

		var STR_URI = 'uri';

		var STR_YOUR_SCORE = 'yourScore';

		var TPL_LABEL_SCORE = '{desc} ({totalEntries} {voteLabel})';
		var TPL_LABEL_SCORE_STACKED = '({totalEntries} {voteLabel})';

		var buffer = [];

		var Ratings = A.Component.create({
			_INSTANCES: {},

			_registerRating(config) {
				var instance = this;

				var ratings = null;

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

				var ratingInstance = null;

				if (ratings && document.getElementById(config.containerId)) {
					ratingInstance = new ratings(config);

					instance._INSTANCES[
						config.id || config.namespace
					] = ratingInstance;
				}

				return ratingInstance;
			},

			_registerTask: A.debounce(() => {
				buffer.forEach(item => {
					var handle = item.container.on(
						EVENT_INTERACTIONS_RENDER,
						event => {
							handle.detach();

							var config = item.config;

							config.initialFocus = event.type === 'focus';

							Ratings._registerRating(config);
						}
					);
				});

				buffer.length = 0;
			}, 100),

			_thumbScoreMap: {
				'-1': -1,
				down: 0,
				up: 1
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
						var instance = this;

						var yourScore = value;

						if (
							(instance.get('type') === 'stars' ||
								instance.get('type') === 'stacked-stars') &&
							yourScore === -1.0
						) {
							yourScore = 0;
						}

						return yourScore;
					}
				}
			},

			EXTENDS: A.Base,

			prototype: {
				_bindRatings() {
					var instance = this;

					instance.ratings.after(
						'itemSelect',
						instance._itemSelect,
						instance
					);
				},

				_convertToIndex(score) {
					var scoreIndex = -1;

					if (score === 1.0) {
						scoreIndex = 0;
					}
					else if (score === 0.0) {
						scoreIndex = 1;
					}

					return scoreIndex;
				},

				_fixScore(score) {
					var prefix = '';

					if (score > 0) {
						prefix = '+';
					}

					return prefix + score;
				},

				_getLabel(desc, totalEntries) {
					var instance = this;

					var tplLabel = '';

					if (instance.get('type') === 'stacked-stars') {
						tplLabel = TPL_LABEL_SCORE_STACKED;
					}
					else {
						tplLabel = TPL_LABEL_SCORE;
					}

					var voteLabel = '';

					if (totalEntries === 1) {
						voteLabel = Liferay.Language.get('vote');
					}
					else {
						voteLabel = Liferay.Language.get('votes');
					}

					return Lang.sub(tplLabel, {
						desc,
						totalEntries,
						voteLabel
					});
				},

				_itemSelect: EMPTY_FN,

				_renderRatings: EMPTY_FN,

				_sendVoteRequest(url, score, callback) {
					var instance = this;

					Liferay.fire('ratings:vote', {
						className: instance.get('className'),
						classPK: instance.get('classPK'),
						ratingType: instance.get('type'),
						score
					});

					var data = {
						className: instance.get('className'),
						classPK: instance.get('classPK'),
						p_auth: Liferay.authToken,
						p_l_id: themeDisplay.getPlid(),
						score
					};

					Liferay.Util.fetch(url, {
						body: Liferay.Util.objectToFormData(data),
						method: 'POST'
					})
						.then(response => response.json())
						.then(response => callback.call(instance, response));
				},

				_showScoreTooltip(event) {
					var instance = this;

					var message = '';

					var stars = instance._ratingScoreNode
						.all('.icon-star-on')
						.size();

					if (stars === 1) {
						message = Liferay.Language.get('star');
					}
					else {
						message = Liferay.Language.get('stars');
					}

					Liferay.Portal.ToolTip.show(
						event.currentTarget,
						stars + ' ' + message
					);
				},

				_updateAverageScoreText(averageScore) {
					var instance = this;

					var firstNode = instance._ratingScoreNode.one(
						SELECTOR_RATING_ELEMENT
					);

					if (firstNode) {
						var message = '';

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

						var averageRatingText = Lang.sub(message, [
							averageScore,
							instance.get(STR_SIZE)
						]);

						firstNode.attr('title', averageRatingText);
					}
				},

				_updateScoreText(score) {
					var instance = this;

					var nodes = instance._ratingStarNode.all('.rating-element');

					nodes.each((node, i) => {
						var ratingMessage = '';
						var ratingScore = '';

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
								instance.get(STR_SIZE)
							])
						);
					});
				},

				initializer() {
					var instance = this;

					instance._renderRatings();
				}
			},

			register(config) {
				var instance = this;

				var containerId = config.containerId;

				var container =
					containerId && document.getElementById(config.containerId);

				if (container) {
					buffer.push({
						config,
						container: A.one(container)
					});

					instance._registerTask();
				}
				else {
					instance._registerRating(config);
				}
			}
		});

		var StarRating = A.Component.create({
			ATTRS: {
				initialFocus: {
					validator: Lang.isBoolean
				}
			},

			EXTENDS: Ratings,

			prototype: {
				_itemSelect() {
					var instance = this;

					var score =
						(instance.ratings.get('selectedIndex') + 1) /
						instance.get(STR_SIZE);
					var uri = instance.get(STR_URI);

					instance._sendVoteRequest(
						uri,
						score,
						instance._saveCallback
					);
				},

				_renderRatings() {
					var instance = this;

					var namespace = instance.get(STR_NAMESPACE);

					instance._ratingScoreNode = A.one(
						'#' + namespace + 'ratingScoreContent'
					);
					instance._ratingStarNode = A.one(
						'#' + namespace + 'ratingStarContent'
					);

					if (themeDisplay.isSignedIn()) {
						var yourScore =
							instance.get(STR_YOUR_SCORE) *
							instance.get(STR_SIZE);

						instance.ratings = new A.StarRating({
							boundingBox: '#' + namespace + 'ratingStar',
							canReset: false,
							cssClasses: {
								element: CSS_ICON_STAR_EMPTY,
								hover: CSS_ICON_STAR,
								off: CSS_ICON_STAR_EMPTY,
								on: CSS_ICON_STAR
							},
							defaultSelected: yourScore,
							srcNode: '#' + namespace + 'ratingStarContent'
						}).render();

						if (instance.get(STR_INITIAL_FOCUS)) {
							instance.ratings
								.get('elements')
								.item(0)
								.focus();
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
					var instance = this;

					var description = Liferay.Language.get('average');

					var averageScore =
						response.averageScore * instance.get(STR_SIZE);

					var score = response.score * instance.get(STR_SIZE);

					var label = instance._getLabel(
						description,
						response.totalEntries
					);

					var formattedAverageScore = averageScore.toFixed(1);

					var averageIndex = instance.get('round')
						? Math.round(formattedAverageScore)
						: Math.floor(formattedAverageScore);

					var ratingScore = instance._ratingScoreNode;

					ratingScore.one('.rating-label').html(label);

					ratingScore
						.all(SELECTOR_RATING_ELEMENT)
						.each((item, index) => {
							var fromCssClass = CSS_ICON_STAR;
							var toCssClass = CSS_ICON_STAR_EMPTY;

							if (index < averageIndex) {
								fromCssClass = CSS_ICON_STAR_EMPTY;
								toCssClass = CSS_ICON_STAR;
							}

							item.replaceClass(fromCssClass, toCssClass);
						});

					instance._updateAverageScoreText(formattedAverageScore);
					instance._updateScoreText(score);
				}
			}
		});

		var ThumbRating = A.Component.create({
			ATTRS: {
				initialFocus: {
					validator: Lang.isBoolean
				}
			},

			EXTENDS: Ratings,

			prototype: {
				_createRating() {
					var instance = this;

					var namespace = instance.get(STR_NAMESPACE);

					instance.ratings = new A.ThumbRating({
						boundingBox: '#' + namespace + 'ratingThumb',
						cssClasses: {
							down: '',
							element: 'rating-off',
							hover: 'rating-on',
							off: 'rating-off',
							on: 'rating-on',
							up: ''
						},
						srcNode: '#' + namespace + 'ratingThumbContent'
					}).render();
				},

				_getThumbScores(entries, score) {
					var positiveVotes = Math.floor(score);

					var negativeVotes = entries - positiveVotes;

					return {
						negativeVotes,
						positiveVotes
					};
				},

				_itemSelect() {
					var instance = this;

					var uri = instance.get(STR_URI);
					var value = instance.ratings.get('value');

					var score = Liferay.Ratings._thumbScoreMap[value];

					instance._sendVoteRequest(
						uri,
						score,
						instance._saveCallback
					);
				},

				_renderRatings() {
					var instance = this;

					if (themeDisplay.isSignedIn()) {
						var yourScore = instance.get(STR_YOUR_SCORE);

						var yourScoreIndex = instance._convertToIndex(
							yourScore
						);

						var namespace = instance.get(STR_NAMESPACE);

						instance._createRating();

						if (instance.get(STR_INITIAL_FOCUS)) {
							A.one('#' + namespace + 'ratingThumb a').focus();
						}

						instance._bindRatings();

						instance.ratings.select(yourScoreIndex);
					}
				},

				_saveCallback(response) {
					var instance = this;

					var thumbScore = instance._getThumbScores(
						response.totalEntries,
						response.totalScore
					);

					instance._updateScores(thumbScore);
				},

				_updateScores(thumbScore) {
					var instance = this;

					var ratings = instance.ratings;

					var cssClasses = ratings.get('cssClasses');
					var elements = ratings.get('elements');

					var ratingThumbDown = elements.item(1);
					var ratingThumbUp = elements.item(0);

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
						var cssClassesOn = cssClasses.on;

						var ratingThumbDownCssClassOn = false;

						if (ratingThumbDown) {
							ratingThumbDownCssClassOn = ratingThumbDown.hasClass(
								cssClassesOn
							);
						}

						var ratingThumbUpCssClassOn = false;

						if (ratingThumbUp) {
							ratingThumbUpCssClassOn = ratingThumbUp.hasClass(
								cssClassesOn
							);
						}

						var thumbDownMessage = '';
						var thumbUpMessage = '';

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
				}
			}
		});

		var LikeRatingImpl = A.Component.create({
			EXTENDS: A.ThumbRating,

			NAME: 'LikeRatingImpl',

			prototype: {
				renderUI() {
					var instance = this;

					var cssClasses = instance.get('cssClasses');

					A.ThumbRating.superclass.renderUI.apply(this, arguments);

					var elements = instance.get('elements');

					elements.addClass(cssClasses.off);
					elements.item(0).addClass(cssClasses.up);
				}
			}
		});

		var LikeRating = A.Component.create({
			EXTENDS: ThumbRating,

			NAME: 'LikeRating',

			prototype: {
				_createRating() {
					var instance = this;

					var namespace = instance.get(STR_NAMESPACE);

					instance.ratings = new LikeRatingImpl({
						boundingBox: '#' + namespace + 'ratingLike',
						cssClasses: {
							down: '',
							element: 'rating-off',
							hover: 'rating-on',
							off: 'rating-off',
							on: 'rating-on',
							up: ''
						},
						srcNode: '#' + namespace + 'ratingLikeContent'
					}).render();
				},

				_getThumbScores(entries) {
					return {
						positiveVotes: entries
					};
				}
			}
		});

		Ratings.LikeRating = LikeRating;
		Ratings.StarRating = StarRating;
		Ratings.ThumbRating = ThumbRating;

		Liferay.Ratings = Ratings;
	},
	'',
	{
		requires: ['aui-rating']
	}
);
