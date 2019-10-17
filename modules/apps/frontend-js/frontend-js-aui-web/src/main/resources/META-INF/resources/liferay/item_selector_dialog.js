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
	'liferay-item-selector-dialog',
	A => {
		var Lang = A.Lang;

		var Util = Liferay.Util;

		var STR_EVENT_NAME = 'eventName';

		var STR_SELECTED_ITEM = 'selectedItem';

		var STR_VISIBLE = 'visible';

		var LiferayItemSelectorDialog = A.Component.create({
			ATTRS: {
				dialogClasses: {
					validator: Lang.isString
				},

				eventName: {
					validator: Lang.isString
				},

				selectedItem: {},

				strings: {
					value: {
						add: Liferay.Language.get('add'),
						cancel: Liferay.Language.get('cancel')
					}
				},

				title: {
					validator: Lang.isString,
					value: Liferay.Language.get('select-file')
				},

				url: {
					validator: Lang.isString
				},

				visible: {
					validator: Lang.isBoolean,
					value: false
				},

				zIndex: {
					validator: Lang.isNumber
				}
			},

			NAME: 'item-selector-dialog',

			NS: 'item-selector-dialog',

			prototype: {
				_onItemSelected(event) {
					var instance = this;

					var currentItem = event.data;

					var dialog = Util.getWindow(instance.get(STR_EVENT_NAME));

					var addButton = dialog
						.getToolbar('footer')
						.get('boundingBox')
						.one('#addButton');

					Util.toggleDisabled(addButton, currentItem.length < 1);

					instance._currentItem = currentItem;
				},

				/*
				 * @deprecated since 7.2, unused
				 */
				close() {
					var instance = this;

					Util.getWindow(instance.get(STR_EVENT_NAME)).hide();
				},

				open() {
					var instance = this;

					var strings = instance.get('strings');

					var eventName = instance.get(STR_EVENT_NAME);

					var zIndex = instance.get('zIndex');

					instance._currentItem = null;
					instance._selectedItem = null;

					instance.set(STR_VISIBLE, true);

					Util.selectEntity(
						{
							dialog: {
								constrain: true,
								cssClass: instance.get('dialogClasses'),
								destroyOnHide: true,
								modal: true,
								on: {
									visibleChange(event) {
										if (!event.newVal) {
											instance.set(
												STR_SELECTED_ITEM,
												instance._selectedItem
											);
										}

										instance.set(STR_VISIBLE, event.newVal);
									}
								},
								'toolbars.footer': [
									{
										cssClass: 'btn-link close-modal',
										id: 'cancelButton',
										label: strings.cancel,
										on: {
											click() {
												instance.close();
											}
										}
									},
									{
										cssClass: 'btn-primary',
										disabled: true,
										id: 'addButton',
										label: strings.add,
										on: {
											click() {
												instance._selectedItem =
													instance._currentItem;
												instance.close();
											}
										}
									}
								],
								zIndex
							},
							eventName,
							id: eventName,
							stack: !zIndex,
							title: instance.get('title'),
							uri: instance.get('url')
						},
						A.bind(instance._onItemSelected, instance)
					);
				}
			}
		});

		A.LiferayItemSelectorDialog = LiferayItemSelectorDialog;
	},
	'',
	{
		requires: ['aui-component']
	}
);
