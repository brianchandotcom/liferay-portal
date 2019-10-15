/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.sharepoint.rest.oauth2.web.internal.connected.app;

import com.liferay.connected.app.ConnectedApp;
import com.liferay.connected.app.ConnectedAppProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.sharepoint.rest.oauth2.service.SharepointOAuth2TokenEntryLocalService;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = ConnectedAppProvider.class)
public class SharepointRestRepositoryConnectedAppProvider
	implements ConnectedAppProvider {

	@Override
	public ConnectedApp getConnectedApp(User user) throws PortalException {
		int count =
			_sharepointOAuth2TokenEntryLocalService.
				getUserSharepointOAuth2TokenEntriesCount(user.getUserId());

		if (count == 0) {
			return null;
		}

		return new SharepointRestConnectedApp(user.getUserId());
	}

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.sharepoint.rest.oauth2.web)"
	)
	private ResourceBundleLoader _resourceBundleLoader;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.sharepoint.rest.oauth2.web)"
	)
	private ServletContext _servletContext;

	@Reference
	private SharepointOAuth2TokenEntryLocalService
		_sharepointOAuth2TokenEntryLocalService;

	private class SharepointRestConnectedApp implements ConnectedApp {

		public SharepointRestConnectedApp(long userId) {
			_userId = userId;
		}

		@Override
		public String getImageURL() {
			return _servletContext.getContextPath() + "/images/sharepoint.png";
		}

		@Override
		public String getKey() {
			return "sharepoint-rest-repository";
		}

		@Override
		public String getName(Locale locale) {
			ResourceBundle resourceBundle =
				_resourceBundleLoader.loadResourceBundle(locale);

			return LanguageUtil.get(resourceBundle, getKey());
		}

		@Override
		public void revoke() {
			_sharepointOAuth2TokenEntryLocalService.
				deleteUserSharepointOAuth2TokenEntries(_userId);
		}

		private final long _userId;

	}

}