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

package com.liferay.lcs.messaging.analytics;

/**
 * @author Ivica Cardic
 */
public class ATAnalyticsEventsMessage extends AnalyticsEventsMessage {

	public static class Context extends AnalyticsEventsMessage.Context {

		public long getAnonymousUserId() {
			return _anonymousUserId;
		}

		public String getBCP47LanguageId() {
			return _bcp47LanguageId;
		}

		public String getCDNBaseURL() {
			return _cdnBaseURL;
		}

		public String getCDNDynamicResourcesHost() {
			return _cdnDynamicResourcesHost;
		}

		public String getCDNHost() {
			return _cdnHost;
		}

		public long getCompanyGroupId() {
			return _companyGroupId;
		}

		public String getDefaultLanguageId() {
			return _defaultLanguageId;
		}

		public String getDoAsUserIdEncoded() {
			return _doAsUserIdEncoded;
		}

		public long getLayoutId() {
			return _layoutId;
		}

		public String getLayoutRelativeURL() {
			return _layoutRelativeURL;
		}

		public long getParentGroupId() {
			return _parentGroupId;
		}

		public long getParentLayoutId() {
			return _parentLayoutId;
		}

		public String getPathContext() {
			return _pathContext;
		}

		public String getPathImage() {
			return _pathImage;
		}

		public String getPathJavaScript() {
			return _pathJavaScript;
		}

		public String getPathMain() {
			return _pathMain;
		}

		public String getPathThemeImages() {
			return _pathThemeImages;
		}

		public String getPathThemeRoot() {
			return _pathThemeRoot;
		}

		public long getPlid() {
			return _plid;
		}

		public String getPortalURL() {
			return _portalURL;
		}

		public long getScopeGroupId() {
			return _scopeGroupId;
		}

		public long getScopeGroupIdOrLiveGroupId() {
			return _scopeGroupIdOrLiveGroupId;
		}

		public String getSessionId() {
			return _sessionId;
		}

		public long getSiteGroupId() {
			return _siteGroupId;
		}

		public String getURLControlPanel() {
			return _urlControlPanel;
		}

		public String getURLHome() {
			return _urlHome;
		}

		public String getUserName() {
			return _userName;
		}

		public boolean isAddSessionIdToURL() {
			return _addSessionIdToURL;
		}

		public boolean isControlPanel() {
			return _controlPanel;
		}

		public boolean isFreeformLayout() {
			return _freeformLayout;
		}

		public boolean isImpersonated() {
			return _impersonated;
		}

		public boolean isPrivateLayout() {
			return _privateLayout;
		}

		public boolean isSignedIn() {
			return _signedIn;
		}

		public boolean isStateExclusive() {
			return _stateExclusive;
		}

		public boolean isStateMaximized() {
			return _stateMaximized;
		}

		public boolean isStatePopUp() {
			return _statePopUp;
		}

		public boolean isVirtualLayout() {
			return _virtualLayout;
		}

		public void setAddSessionIdToURL(boolean addSessionIdToURL) {
			_addSessionIdToURL = addSessionIdToURL;
		}

		public void setAnonymousUserId(long anonymousUserId) {
			_anonymousUserId = anonymousUserId;
		}

		public void setBCP47LanguageId(String bcp47LanguageId) {
			_bcp47LanguageId = bcp47LanguageId;
		}

		public void setCDNBaseURL(String cdnBaseURL) {
			_cdnBaseURL = cdnBaseURL;
		}

		public void setCDNDynamicResourcesHost(String cdnDynamicResourcesHost) {
			_cdnDynamicResourcesHost = cdnDynamicResourcesHost;
		}

		public void setCDNHost(String cdnHost) {
			_cdnHost = cdnHost;
		}

		public void setCompanyGroupId(long companyGroupId) {
			_companyGroupId = companyGroupId;
		}

		public void setControlPanel(boolean controlPanel) {
			_controlPanel = controlPanel;
		}

		public void setDefaultLanguageId(String defaultLanguageId) {
			_defaultLanguageId = defaultLanguageId;
		}

		public void setDoAsUserIdEncoded(String doAsUserIdEncoded) {
			_doAsUserIdEncoded = doAsUserIdEncoded;
		}

		public void setFreeformLayout(boolean freeformLayout) {
			_freeformLayout = freeformLayout;
		}

		public void setImpersonated(boolean impersonated) {
			_impersonated = impersonated;
		}

		public void setLayoutId(long layoutId) {
			_layoutId = layoutId;
		}

		public void setLayoutRelativeURL(String layoutRelativeURL) {
			_layoutRelativeURL = layoutRelativeURL;
		}

		public void setParentGroupId(long parentGroupId) {
			_parentGroupId = parentGroupId;
		}

		public void setParentLayoutId(long parentLayoutId) {
			_parentLayoutId = parentLayoutId;
		}

		public void setPathContext(String pathContext) {
			_pathContext = pathContext;
		}

		public void setPathImage(String pathImage) {
			_pathImage = pathImage;
		}

		public void setPathJavaScript(String pathJavaScript) {
			_pathJavaScript = pathJavaScript;
		}

		public void setPathMain(String pathMain) {
			_pathMain = pathMain;
		}

		public void setPathThemeImages(String pathThemeImages) {
			_pathThemeImages = pathThemeImages;
		}

		public void setPathThemeRoot(String pathThemeRoot) {
			_pathThemeRoot = pathThemeRoot;
		}

		public void setPlid(long plid) {
			_plid = plid;
		}

		public void setPortalURL(String portalURL) {
			_portalURL = portalURL;
		}

		public void setPrivateLayout(boolean privateLayout) {
			_privateLayout = privateLayout;
		}

		public void setScopeGroupId(long scopeGroupId) {
			_scopeGroupId = scopeGroupId;
		}

		public void setScopeGroupIdOrLiveGroupId(
			long scopeGroupIdOrLiveGroupId) {

			_scopeGroupIdOrLiveGroupId = scopeGroupIdOrLiveGroupId;
		}

		public void setSessionId(String sessionId) {
			_sessionId = sessionId;
		}

		public void setSignedIn(boolean signedIn) {
			_signedIn = signedIn;
		}

		public void setSiteGroupId(long siteGroupId) {
			_siteGroupId = siteGroupId;
		}

		public void setStateExclusive(boolean stateExclusive) {
			_stateExclusive = stateExclusive;
		}

		public void setStateMaximized(boolean stateMaximized) {
			_stateMaximized = stateMaximized;
		}

		public void setStatePopUp(boolean statePopUp) {
			_statePopUp = statePopUp;
		}

		public void setUrlControlPanel(String urlControlPanel) {
			_urlControlPanel = urlControlPanel;
		}

		public void setURLHome(String urlHome) {
			_urlHome = urlHome;
		}

		public void setUserName(String userName) {
			_userName = userName;
		}

		public void setVirtualLayout(boolean virtualLayout) {
			_virtualLayout = virtualLayout;
		}

		private boolean _addSessionIdToURL;
		private long _anonymousUserId;
		private String _bcp47LanguageId;
		private String _cdnBaseURL;
		private String _cdnDynamicResourcesHost;
		private String _cdnHost;
		private long _companyGroupId;
		private boolean _controlPanel;
		private String _defaultLanguageId;
		private String _doAsUserIdEncoded;
		private boolean _freeformLayout;
		private boolean _impersonated;
		private long _layoutId;
		private String _layoutRelativeURL;
		private long _parentGroupId;
		private long _parentLayoutId;
		private String _pathContext;
		private String _pathImage;
		private String _pathJavaScript;
		private String _pathMain;
		private String _pathThemeImages;
		private String _pathThemeRoot;
		private long _plid;
		private String _portalURL;
		private boolean _privateLayout;
		private long _scopeGroupId;
		private long _scopeGroupIdOrLiveGroupId;
		private String _sessionId;
		private boolean _signedIn;
		private long _siteGroupId;
		private boolean _stateExclusive;
		private boolean _stateMaximized;
		private boolean _statePopUp;
		private String _urlControlPanel;
		private String _urlHome;
		private String _userName;
		private boolean _virtualLayout;

	}

}