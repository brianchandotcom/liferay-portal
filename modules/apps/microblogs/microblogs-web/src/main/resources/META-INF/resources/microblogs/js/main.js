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

AUI().use(
	'aui-base',
	'aui-io-plugin-deprecated',
	'liferay-portlet-url',
	'liferay-util-window',
	function(A) {
		Liferay.namespace('Microblogs');

		Liferay.Microblogs = {
			init: function(param) {
				var instance = this;

				instance._baseActionURL = param.baseActionURL;
				instance._microblogsEntriesURL = param.microblogsEntriesURL;
			},

			closePopup: function() {
				var instance = this;

				var popup = instance.getPopup();

				if (popup) {
					popup.hide();
				}
			},

			displayPopup: function(url, title) {
				var instance = this;

				var popup = instance.getPopup();

				popup.show();

				popup.titleNode.html(title);

				popup.io.set('uri', url);

				popup.io.start();
			},

			getPopup: function() {
				var instance = this;

				if (!instance._popup) {
					instance._popup = Liferay.Util.Window.getWindow({
						dialog: {
							centered: true,
							constrain2view: true,
							cssClass: 'microblogs-portlet',
							modal: true,
							resizable: false,
							width: 475
						}
					})
						.plug(A.Plugin.IO, {
							autoLoad: false
						})
						.render();
				}

				return instance._popup;
			},

			updateMicroblogs: function(form, url, updateContainer) {
				var instance = this;

				Liferay.Util.fetch(form.getAttribute('action'), {
					body: new FormData(form.getDOM()),
					method: 'POST'
				}).then(function() {
					instance.updateMicroblogsList(url, updateContainer);

					Liferay.fire('microblogPosted');
				});
			},

			updateMicroblogsList: function(url, updateContainer) {
				var instance = this;

				instance._micrblogsEntries = updateContainer;

				if (!instance._micrblogsEntries.io) {
					instance._micrblogsEntries.plug(A.Plugin.IO, {
						autoLoad: false
					});
				}

				if (!url) {
					url = instance._microblogsEntriesURL;
				}

				instance._micrblogsEntries.io.set('uri', url);

				instance._micrblogsEntries.io.start();
			},

			updateViewCount: function(microblogsEntryId) {
				var instance = this;

				var portletURL = new Liferay.PortletURL.createURL(
					instance._baseActionURL
				);

				portletURL.setParameter(
					'javax.portlet.action',
					'updateMicroblogsEntryViewCount'
				);
				portletURL.setParameter('microblogsEntryId', microblogsEntryId);
				portletURL.setWindowState('normal');

				Liferay.Util.fetch(portletURL.toString(), {
					method: 'POST'
				});
			}
		};

		Liferay.on('sessionExpired', function(event) {
			var reload = function() {
				window.location.reload();
			};

			Liferay.Microblogs.displayPopup = reload;
			Liferay.Microblogs.updateMicroblogs = reload;
			Liferay.Microblogs.updateMicroblogsList = reload;
		});
	}
);
