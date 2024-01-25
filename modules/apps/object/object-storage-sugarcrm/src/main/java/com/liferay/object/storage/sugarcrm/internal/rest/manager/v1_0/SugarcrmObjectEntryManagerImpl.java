/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.storage.sugarcrm.internal.rest.manager.v1_0;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntryModel;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.list.type.entry.util.ListTypeEntryUtil;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.constants.ObjectActionKeys;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.field.business.type.ObjectFieldBusinessType;
import com.liferay.object.field.business.type.ObjectFieldBusinessTypeRegistry;
import com.liferay.object.field.setting.util.ObjectFieldSettingUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.rest.dto.v1_0.ListEntry;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.dto.v1_0.Status;
import com.liferay.object.rest.dto.v1_0.util.CreatorUtil;
import com.liferay.object.rest.filter.factory.FilterFactory;
import com.liferay.object.rest.manager.exception.ObjectEntryManagerHttpException;
import com.liferay.object.rest.manager.v1_0.BaseObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.storage.sugarcrm.configuration.SugarcrmConfiguration;
import com.liferay.object.storage.sugarcrm.internal.web.cache.SugarcrmAccessTokenWebCacheItem;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.InlineSQLHelper;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.math.BigDecimal;

import java.net.HttpURLConnection;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Maurice Sepe
 */
@Component(
    property = "object.entry.manager.storage.type=" + ObjectDefinitionConstants.STORAGE_TYPE_SUGARCRM,
    service = ObjectEntryManager.class
)
public class SugarcrmObjectEntryManagerImpl
    extends BaseObjectEntryManager implements ObjectEntryManager {

    @Override
    public ObjectEntry addObjectEntry(
            DTOConverterContext dtoConverterContext,
            ObjectDefinition objectDefinition, ObjectEntry objectEntry,
            String scopeKey)
        throws Exception {

        checkPortletResourcePermission(
            ObjectActionKeys.ADD_OBJECT_ENTRY, objectDefinition, scopeKey,
            dtoConverterContext.getUser());

    return null;
    }

    @Override
    public void deleteObjectEntry(
            long companyId, DTOConverterContext dtoConverterContext,
            String externalReferenceCode, ObjectDefinition objectDefinition,
            String scopeKey)
        throws Exception {

        checkPortletResourcePermission(
            ActionKeys.DELETE, objectDefinition, scopeKey,
            dtoConverterContext.getUser());
    }

    @Override
    public Page<ObjectEntry> getObjectEntries(
            long companyId, ObjectDefinition objectDefinition, String scopeKey,
            Aggregation aggregation, DTOConverterContext dtoConverterContext,
            String filterString, Pagination pagination, String search,
            Sort[] sorts)
        throws Exception {

        checkPortletResourcePermission(
            ActionKeys.VIEW, objectDefinition, scopeKey,
            dtoConverterContext.getUser());

    return null;
    }

    @Override
    public ObjectEntry getObjectEntry(
            long companyId, DTOConverterContext dtoConverterContext,
            String externalReferenceCode, ObjectDefinition objectDefinition,
            String scopeKey)
        throws Exception {

        checkPortletResourcePermission(
            ActionKeys.VIEW, objectDefinition, scopeKey,
            dtoConverterContext.getUser());

        if (Validator.isNull(externalReferenceCode)) {
            return null;
        }

        return null;
    }

    @Override
    public String getStorageLabel(Locale locale) {
        return language.get(
            locale, ObjectDefinitionConstants.STORAGE_TYPE_SUGARCRM);
    }

    @Override
    public String getStorageType() {
        return ObjectDefinitionConstants.STORAGE_TYPE_SUGARCRM;
    }

    @Override
    public ObjectEntry updateObjectEntry(
            long companyId, DTOConverterContext dtoConverterContext,
            String externalReferenceCode, ObjectDefinition objectDefinition,
            ObjectEntry objectEntry, String scopeKey)
        throws Exception {

        checkPortletResourcePermission(
            ActionKeys.UPDATE, objectDefinition, scopeKey,
            dtoConverterContext.getUser());

        return null;
    }

    private final SugarcrmHttp _sugarcrmHttp =  new SugarcrmHttp();

    private class SugarcrmHttp {

        public JSONObject delete(long companyId, long groupId, String location) {
            try {
                return _invoke(
                    companyId, groupId, location, Http.Method.DELETE, null);
            }
            catch (Exception exception) {
                return ReflectionUtil.throwException(exception);
            }
        }
    
        public JSONObject get(
            long companyId, long groupId, String location, JSONObject jsonObject) {
    
            try {
                return _invoke(
                    companyId, groupId, location, Http.Method.GET, jsonObject);
            }
            catch (Exception exception) {
                return ReflectionUtil.throwException(exception);
            }
        }
    
        public JSONObject patch(
            long companyId, long groupId, String location,
            JSONObject bodyJSONObject) {
    
            try {
                return _invoke(
                    companyId, groupId, location, Http.Method.PATCH,
                    bodyJSONObject);
            }
            catch (Exception exception) {
                return ReflectionUtil.throwException(exception);
            }
        }
    
        public JSONObject post(
            long companyId, long groupId, String location,
            JSONObject bodyJSONObject) {
    
            try {
                return _invoke(
                    companyId, groupId, location, Http.Method.POST, bodyJSONObject);
            }
            catch (Exception exception) {
                return ReflectionUtil.throwException(exception);
            }
        }
    
        private JSONObject _getSugarCRMAccessTokenJSONObject(
            SugarcrmConfiguration sugarcrmConfiguration) {
    
            JSONObject jSONObject = SugarcrmAccessTokenWebCacheItem.get(
                sugarcrmConfiguration);
    
            if (jSONObject == null) {
                throw new ObjectEntryManagerHttpException(
                    "Unable to authenticate with SugarCRM");
            }
    
            return jSONObject;
        }
    
        private SugarcrmConfiguration _getSugarcrmConfiguration(
            long companyId, long groupId) {
    
            try {
                if (groupId == 0) {
                    return _configurationProvider.getCompanyConfiguration(
                        SugarcrmConfiguration.class, companyId);
                }
    
                return _configurationProvider.getGroupConfiguration(
                    SugarcrmConfiguration.class, groupId);
            }
            catch (ConfigurationException configurationException) {
                return ReflectionUtil.throwException(configurationException);
            }
        }

        @Reference
	private ConfigurationProvider _configurationProvider;
    
        private JSONObject _invoke(
                long companyId, long groupId, String location, Http.Method method,
                JSONObject bodyJSONObject)
            throws Exception {
    
            byte[] bytes = _invokeAsBytes(
                companyId, groupId, location, method, bodyJSONObject);
    
            if (bytes == null) {
                return _jsonFactory.createJSONObject();
            }
    
            return _jsonFactory.createJSONObject(new String(bytes));
        }
    
        private byte[] _invokeAsBytes(
                long companyId, long groupId, String location, Http.Method method,
                JSONObject bodyJSONObject)
            throws Exception {
    
            Http.Options options = new Http.Options();
    
            if (bodyJSONObject != null) {
                options.addHeader(
                    HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
            }
    
            SugarcrmConfiguration sugarcrmConfiguration = _getSugarcrmConfiguration(
                companyId, groupId);
    
            JSONObject jsonObject = _getSugarCRMAccessTokenJSONObject(
                sugarcrmConfiguration);
    
            options.addHeader(
                "Authorization", "Bearer " + jsonObject.getString("access_token"));
    
            if (bodyJSONObject != null) {
                options.setBody(
                    bodyJSONObject.toString(), ContentTypes.APPLICATION_JSON,
                    StringPool.UTF8);
            }
    
            options.setFollowRedirects(false);
            options.setLocation(
                StringBundler.concat(sugarcrmConfiguration.baseURL(), location));
            options.setMethod(method);
    
            _log.info("SugarCRM connector calling URL: " + options.getLocation());
    
            byte[] bytes = _http.URLtoByteArray(options);
    
            Http.Response response = options.getResponse();
    
            if ((response.getResponseCode() < HttpURLConnection.HTTP_OK) ||
                (response.getResponseCode() >=
                    HttpURLConnection.HTTP_MULT_CHOICE)) {
    
                throw new ObjectEntryManagerHttpException(
                    StringBundler.concat(
                        "Unexpected response code ", response.getResponseCode(),
                        " with response message: ", new String(bytes)));
            }
    
            return bytes;
        }
    
        private final Log _log = LogFactoryUtil.getLog(SugarcrmHttp.class);
    
        @Reference
        private Http _http;
    
        @Reference
        private JSONFactory _jsonFactory;

    }
}