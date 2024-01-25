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

 package com.liferay.object.storage.sugarcrm.internal.web.cache;

 import com.liferay.object.storage.sugarcrm.configuration.SugarcrmConfiguration;
 import com.liferay.petra.string.StringBundler;
 import com.liferay.petra.string.StringPool;
 import com.liferay.portal.kernel.json.JSONFactoryUtil;
 import com.liferay.portal.kernel.json.JSONObject;
 import com.liferay.portal.kernel.log.Log;
 import com.liferay.portal.kernel.log.LogFactoryUtil;
 import com.liferay.portal.kernel.util.HashMapBuilder;
 import com.liferay.portal.kernel.util.Http;
 import com.liferay.portal.kernel.util.HttpUtil;
 import com.liferay.portal.kernel.util.Time;
 import com.liferay.portal.kernel.webcache.WebCacheItem;
 import com.liferay.portal.kernel.webcache.WebCachePoolUtil;
 
 import java.net.HttpURLConnection;
 
 /**
  * @author Maurice Sepe
  */
 public class SugarcrmAccessTokenWebCacheItem implements WebCacheItem {
 
     public static JSONObject get(SugarcrmConfiguration sugarcrmConfiguration) {
         return (JSONObject)WebCachePoolUtil.get(
             StringBundler.concat(
                SugarcrmAccessTokenWebCacheItem.class.getName(),
                 StringPool.POUND, sugarcrmConfiguration.username()),
             new SugarcrmAccessTokenWebCacheItem(sugarcrmConfiguration));
     }
 
     public SugarcrmAccessTokenWebCacheItem(
         SugarcrmConfiguration sugarcrmConfiguration) {
 
         _sugarcrmConfiguration = sugarcrmConfiguration;
     }
 
     @Override
     public JSONObject convert(String key) {
         try {
             Http.Options options = new Http.Options();
 
             options.setParts(
                 HashMapBuilder.put(
                     "client_id", _sugarcrmConfiguration.clientId()
                 ).put(
                     "grant_type", _sugarcrmConfiguration.grantType()
                 ).put(
                     "password", _sugarcrmConfiguration.password()
                 ).put(
                     "username", _sugarcrmConfiguration.username()
                 ).build());
             options.setLocation(_sugarcrmConfiguration.accessTokenURL());
             options.setPost(true);
 
             String responseJSON = HttpUtil.URLtoString(options);
 
             Http.Response response = options.getResponse();
 
             if (response.getResponseCode() != HttpURLConnection.HTTP_OK) {
                 if (_log.isDebugEnabled()) {
                     _log.debug(
                         StringBundler.concat(
                             "Response code ", response.getResponseCode(), ": ",
                             responseJSON));
                 }
 
                 return null;
             }
 
             return JSONFactoryUtil.createJSONObject(responseJSON);
         }
         catch (Exception exception) {
             if (_log.isDebugEnabled()) {
                 _log.debug(exception);
             }
 
             return null;
         }
     }
 
     @Override
     public long getRefreshTime() {
         return _REFRESH_TIME;
     }
 
     private static final long _REFRESH_TIME = Time.MINUTE * 60;
 
     private static final Log _log = LogFactoryUtil.getLog(
        SugarcrmAccessTokenWebCacheItem.class);
 
     private final SugarcrmConfiguration _sugarcrmConfiguration;
 
 }