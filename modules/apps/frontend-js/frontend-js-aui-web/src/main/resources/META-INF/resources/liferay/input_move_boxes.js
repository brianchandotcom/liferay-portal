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
	'liferay-input-move-boxes',
	(A) => {
		const Util = Liferay.Util;

		const CSS_LEFT_REORDER = 'left-reorder';

		const CSS_RIGHT_REORDER = 'right-reorder';

		const NAME = 'inputmoveboxes';

		const InputMoveBoxes = A.Component.create({
			ATTRS: {
				leftBoxMaxItems: Infinity,

				leftReorder: {},

				rightBoxMaxItems: Infinity,

				rightReorder: {},

				strings: {
					LEFT_MOVE_DOWN: '',
					LEFT_MOVE_UP: '',
					MOVE_LEFT: '',
					MOVE_RIGHT: '',
					RIGHT_MOVE_DOWN: '',
					RIGHT_MOVE_UP: '',
				},
			},

			HTML_PARSER: {
				leftReorder(contentBox) {
					return contentBox.hasClass(CSS_LEFT_REORDER);
				},

				rightReorder(contentBox) {
					return contentBox.hasClass(CSS_RIGHT_REORDER);
				},
			},

			NAME,

			prototype: {
				_afterMoveClick(event) {
					const instance = this;

					const target = event.domEvent.target;
					const targetBtn = target.ancestor('.btn', true);

					if (targetBtn) {
						const cssClass = targetBtn.get('className');

						let from = instance._leftBox;
						let to = instance._rightBox;

						let sort = !instance.get('rightReorder');

						if (cssClass.indexOf('move-left') !== -1) {
							from = instance._rightBox;
							to = instance._leftBox;

							sort = !instance.get('leftReorder');
						}

						instance._moveItem(from, to, sort);
						instance._toggleReorderToolbars();
					}
				},

				_afterOrderClick(event, box) {
					const instance = this;

					const target = event.domEvent.target;
					const targetBtn = target.ancestor('.btn', true);

					if (targetBtn) {
						const cssClass = targetBtn.get('className');

						let direction = 1;

						if (cssClass.indexOf('reorder-up') !== -1) {
							direction = 0;
						}

						instance._orderItem(box, direction);
					}
				},

				_moveItem(from, to, sort) {
					const instance = this;

					from = A.one(from);
					to = A.one(to);

					const selectedIndex = from.get('selectedIndex');

					let selectedOption;

					if (selectedIndex >= 0) {
						const options = from.all('option');

						selectedOption = options.item(selectedIndex);

						options.each((item) => {
							if (item.get('selected')) {
								to.append(item);
							}
						});
					}

					if (
						selectedOption &&
						selectedOption.text() !== '' &&
						sort === true
					) {
						instance.sortBox(to);
					}

					Liferay.fire(NAME + ':moveItem', {
						fromBox: from,
						toBox: to,
					});
				},

				_onSelectChange(event) {
					const instance = this;

					instance._toggleBtnMove(event);
					instance._toggleBtnSort(event);
				},

				_onSelectFocus(event, box) {
					const instance = this;

					instance._toggleBtnMove(event);

					box.attr('selectedIndex', '-1');
				},

				_orderItem(box, direction) {
					Util.reorder(box, direction);

					Liferay.fire(NAME + ':orderItem', {
						box,
						direction,
					});
				},

				_renderBoxes() {
					const instance = this;

					const contentBox = instance.get('contentBox');

					instance._leftBox = contentBox.one('.left-selector');
					instance._rightBox = contentBox.one('.right-selector');
				},

				_renderButtons() {
					const instance = this;

					const contentBox = instance.get('contentBox');
					const strings = instance.get('strings');

					const moveButtonsColumn = contentBox.one(
						'.move-arrow-buttons'
					);

					if (moveButtonsColumn) {
						const moveToolbar = new A.Toolbar({
							children: [
								[
									'normal',
									'vertical',
									{
										cssClass: 'move-right',
										icon: 'angle-right',
										on: {
											click(event) {
												event.domEvent.preventDefault();
											},
										},
										title: strings.MOVE_RIGHT,
									},
									{
										cssClass: 'move-left',
										icon: 'angle-left',
										on: {
											click(event) {
												event.domEvent.preventDefault();
											},
										},
										title: strings.MOVE_LEFT,
									},
								],
							],
						});

						moveToolbar.get(
							'toolbarRenderer'
						).TEMPLATES.icon = Liferay.Util.getLexiconIconTpl(
							'{cssClass}'
						);

						moveToolbar.render(moveButtonsColumn);

						instance._moveToolbar = moveToolbar;
					}

					const config_reorder = {
						children: [
							[
								{
									cssClass: 'reorder-up',
									icon: 'angle-up',
									on: {
										click(event) {
											event.domEvent.preventDefault();
										},
									},
								},
								{
									cssClass: 'reorder-down',
									icon: 'angle-down',
									on: {
										click(event) {
											event.domEvent.preventDefault();
										},
									},
								},
							],
						],
					};

					if (instance.get('leftReorder')) {
						const leftColumn = contentBox.one(
							'.left-selector-column'
						);

						config_reorder.children[0][0].title =
							strings.LEFT_MOVE_UP;
						config_reorder.children[0][1].title =
							strings.LEFT_MOVE_DOWN;

						instance._leftReorderToolbar = new A.Toolbar(
							config_reorder
						).render(leftColumn);
					}

					if (instance.get('rightReorder')) {
						const rightColumn = contentBox.one(
							'.right-selector-column'
						);

						config_reorder.children[0][0].title =
							strings.RIGHT_MOVE_UP;
						config_reorder.children[0][1].title =
							strings.RIGHT_MOVE_DOWN;

						instance._rightReorderToolbar = new A.Toolbar(
							config_reorder
						).render(rightColumn);
					}

					instance._toggleReorderToolbars();
				},

				_toggleBtnMove(event) {
					const instance = this;

					const sourceBox = event.target;

					const selectedOptions = sourceBox
						.get('options')
						.getDOMNodes()
						.filter((option) => option.selected);

					const direction =
						sourceBox === instance._rightBox ? 'left' : 'right';

					const destinationBox = instance[`_${direction}Box`];
					const destinationBoxMaxItems = instance.get(
						`${direction}BoxMaxItems`
					);

					const contentBox = instance.get('contentBox');

					instance._toggleBtnState(
						contentBox.one(`.move-${direction}`),
						destinationBox.get('length') + selectedOptions.length >
							destinationBoxMaxItems
					);
				},

				_toggleBtnSort(event) {
					const instance = this;

					const contentBox = instance.get('contentBox');

					const sortBtnDown = contentBox.one('.reorder-down');
					const sortBtnUp = contentBox.one('.reorder-up');

					const currentTarget = event.currentTarget;

					if (currentTarget && sortBtnDown && sortBtnUp) {
						const length = currentTarget.get('length');
						const selectedIndex = currentTarget.get(
							'selectedIndex'
						);

						let btnDisabledDown = false;
						let btnDisabledUp = false;

						if (selectedIndex === length - 1) {
							btnDisabledDown = true;
						}
						else if (selectedIndex === 0) {
							btnDisabledUp = true;
						}
						else if (selectedIndex === -1) {
							btnDisabledDown = true;
							btnDisabledUp = true;
						}

						instance._toggleBtnState(sortBtnDown, btnDisabledDown);
						instance._toggleBtnState(sortBtnUp, btnDisabledUp);
					}
				},

				_toggleBtnState(button, state) {
					Util.toggleDisabled(button, state);
				},

				_toggleReorderToolbar(sideReorderToolbar, sideColumn) {
					const showReorderToolbar =
						sideColumn.all('option').size() > 1;

					sideReorderToolbar.toggle(showReorderToolbar);
				},

				_toggleReorderToolbars() {
					const instance = this;

					const contentBox = instance.get('contentBox');

					if (instance.get('leftReorder')) {
						const leftColumn = contentBox.one(
							'.left-selector-column'
						);

						instance._toggleReorderToolbar(
							instance._leftReorderToolbar,
							leftColumn
						);
					}

					if (instance.get('rightReorder')) {
						const rightColumn = contentBox.one(
							'.right-selector-column'
						);

						instance._toggleReorderToolbar(
							instance._rightReorderToolbar,
							rightColumn
						);
					}
				},

				bindUI() {
					const instance = this;

					const leftReorderToolbar = instance._leftReorderToolbar;

					if (leftReorderToolbar) {
						leftReorderToolbar.after(
							'click',
							A.rbind(
								'_afterOrderClick',
								instance,
								instance._leftBox
							)
						);
					}

					const rightReorderToolbar = instance._rightReorderToolbar;

					if (rightReorderToolbar) {
						rightReorderToolbar.after(
							'click',
							A.rbind(
								'_afterOrderClick',
								instance,
								instance._rightBox
							)
						);
					}

					instance._moveToolbar.on(
						'click',
						instance._afterMoveClick,
						instance
					);

					instance._leftBox.after(
						'change',
						A.bind('_onSelectChange', instance)
					);

					instance._leftBox.on(
						'focus',
						A.rbind('_onSelectFocus', instance, instance._rightBox)
					);

					instance._rightBox.after(
						'change',
						A.bind('_onSelectChange', instance)
					);

					instance._rightBox.on(
						'focus',
						A.rbind('_onSelectFocus', instance, instance._leftBox)
					);
				},

				renderUI() {
					const instance = this;

					instance._renderBoxes();
					instance._renderButtons();
				},

				sortBox(box) {
					const newBox = [];

					const options = box.all('option');

					for (let i = 0; i < options.size(); i++) {
						newBox[i] = [
							options.item(i).val(),
							options.item(i).text(),
						];
					}

					newBox.sort((a, b) => {
						a = a[1].toLowerCase();
						b = b[1].toLowerCase();

						if (a > b) {
							return 1;
						}

						if (a < b) {
							return -1;
						}

						return 0;
					});

					const boxObj = A.one(box);

					boxObj.all('option').remove(true);

					newBox.forEach((item) => {
						boxObj.append(
							'<option value="' +
								item[0] +
								'">' +
								item[1] +
								'</option>'
						);
					});
				},
			},
		});

		Liferay.InputMoveBoxes = InputMoveBoxes;
	},
	'',
	{
		requires: ['aui-base', 'aui-toolbar'],
	}
);
