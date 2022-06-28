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
 * The List View Component.
 *
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 * @module liferay-list-view
 */

AUI.add(
	'liferay-list-view',
	(A) => {
		const Lang = A.Lang;

		const isString = Lang.isString;

		const CONTENT_BOX = 'contentBox';

		const CSS_DATA_CONTAINER = 'lfr-list-view-data-container';

		const NAME = 'listview';

		const STR_BOTTOM = 'bottom';

		const STR_LEFT = 'left';

		const STR_REGION = 'region';

		const STR_RIGHT = 'right';

		const STR_TOP = 'top';

		const TPL_DATA_CONTAINER =
			'<div class="' + CSS_DATA_CONTAINER + ' hide"></div>';

		const UI_SRC = A.Widget.UI_SRC;

		const ListView = A.Component.create({
			ATTRS: {
				cssClass: {
					value: 'lfr-list-view',
				},
				data: {
					setter: '_setData',
					validator: '_validateData',
					value: null,
				},

				direction: {
					validator: '_validateDirection',
					value: STR_LEFT,
				},

				item: {
					validator: Lang.isObject,
					value: null,
				},

				itemChosenEvent: {
					validator: isString,
					value: 'click',
				},

				itemSelector: {
					validator: isString,
					value: null,
				},

				transitionConfig: {
					validator: Lang.isObject,
					value: {
						duration: 0.3,
						easing: 'ease-out',
						left: 0,
						top: 0,
					},
				},

				useTransition: {
					validator: Lang.isBoolean,
					value: true,
				},
			},

			NAME,

			prototype: {
				_afterDataChange(event) {
					const instance = this;

					const useTransition = instance.get('useTransition');

					const newData = event.newVal;

					if (useTransition) {
						const dataContainer = instance._dataContainer;

						dataContainer.plug(A.Plugin.ParseContent);

						dataContainer.setContent(newData);

						instance._moveContainer();
					}
					else {
						const contentBox = instance.get(CONTENT_BOX);

						contentBox.plug(A.Plugin.ParseContent);

						contentBox.setContent(newData);
					}
				},

				_defTransitionCompletedFn() {
					const instance = this;

					const dataContainer = instance._dataContainer;

					instance
						.get(CONTENT_BOX)
						.setContent(dataContainer.getDOM().childNodes);

					dataContainer.hide();
					dataContainer.empty();
				},

				_moveContainer() {
					const instance = this;

					const contentBox = instance.get(CONTENT_BOX);

					const targetRegion = contentBox.get(STR_REGION);

					instance._setDataContainerPosition(targetRegion);

					const dataContainer = instance._dataContainer;

					dataContainer.show();

					const transitionConfig = instance.get('transitionConfig');

					dataContainer.transition(
						transitionConfig,
						instance._transitionCompleteProxy
					);
				},

				_onItemChosen(event) {
					const instance = this;

					event.preventDefault();

					instance.set('item', event.currentTarget, {
						src: UI_SRC,
					});
				},

				_setData(value) {
					if (isString(value)) {
						value = A.Node.create(value);
					}

					return value;
				},

				_setDataContainerPosition(targetRegion) {
					const instance = this;

					targetRegion =
						targetRegion ||
						instance.get(CONTENT_BOX).get(STR_REGION);

					const direction = instance.get('direction');

					const dataContainer = instance._dataContainer;

					const styles = {
						left: 0,
						top: 0,
					};

					if (direction === STR_LEFT) {
						styles.left = targetRegion.width;
					}
					else if (direction === STR_RIGHT) {
						styles.left = -targetRegion.width;
					}
					else if (direction === STR_TOP) {
						styles.top = -targetRegion.height;
					}
					else if (direction === STR_BOTTOM) {
						styles.top = targetRegion.height;
					}

					dataContainer.setStyles(styles);
				},

				_validateData(value) {
					return isString(value) || A.instanceOf(value, A.Node);
				},

				_validateDirection(value) {
					return (
						value === STR_BOTTOM ||
						value === STR_LEFT ||
						value === STR_RIGHT ||
						value === STR_TOP
					);
				},

				bindUI() {
					const instance = this;

					const contentBox = instance.get(CONTENT_BOX);

					const itemChosenEvent = instance.get('itemChosenEvent');
					const itemSelector = instance.get('itemSelector');

					instance._itemChosenHandle = contentBox.delegate(
						itemChosenEvent,
						instance._onItemChosen,
						itemSelector,
						instance
					);

					instance.after('dataChange', instance._afterDataChange);

					instance.publish('transitionComplete', {
						defaultFn: instance._defTransitionCompletedFn,
					});
				},

				destructor() {
					const instance = this;

					if (instance._itemChosenHandle) {
						instance._itemChosenHandle.detach();
					}

					if (instance._dataContainer) {
						instance._dataContainer.destroy(true);
					}
				},

				initializer() {
					const instance = this;

					instance._transitionCompleteProxy = A.fn(
						instance.fire,
						instance,
						'transitionComplete'
					);
				},

				renderUI() {
					const instance = this;

					const boundingBox = instance.get('boundingBox');

					const dataContainer = A.Node.create(TPL_DATA_CONTAINER);

					boundingBox.append(dataContainer);

					instance._dataContainer = dataContainer;
				},
			},
		});

		Liferay.ListView = ListView;
	},
	'',
	{
		requires: ['aui-base', 'transition'],
	}
);
