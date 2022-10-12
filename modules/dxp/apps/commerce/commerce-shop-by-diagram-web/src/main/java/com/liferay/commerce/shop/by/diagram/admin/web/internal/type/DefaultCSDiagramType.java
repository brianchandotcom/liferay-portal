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

package com.liferay.commerce.shop.by.diagram.admin.web.internal.type;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.url.CPFriendlyURL;
import com.liferay.commerce.shop.by.diagram.admin.web.internal.constants.CSDiagramFDSNames;
import com.liferay.commerce.shop.by.diagram.admin.web.internal.util.CSDiagramSettingUtil;
import com.liferay.commerce.shop.by.diagram.configuration.CSDiagramSettingImageConfiguration;
import com.liferay.commerce.shop.by.diagram.constants.CSDiagramWebKeys;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramSetting;
import com.liferay.commerce.shop.by.diagram.type.CSDiagramType;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	configurationPid = "com.liferay.commerce.shop.by.diagram.configuration.CSDiagramSettingImageConfiguration",
	immediate = true,
	property = {
		"commerce.product.definition.diagram.type.key=" + DefaultCSDiagramType.KEY,
		"commerce.product.definition.diagram.type.order:Integer=100"
	},
	service = CSDiagramType.class
)
public class DefaultCSDiagramType implements CSDiagramType {

	public static final String KEY = "diagram.type.default";

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return _languagea.get(resourceBundle, "default");
	}

	@Override
	public void render(
			CSDiagramSetting csDiagramSetting,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		httpServletRequest.setAttribute(
			CSDiagramWebKeys.CS_DIAGRAM_CP_TYPE_PROPS,
			_getProps(csDiagramSetting, httpServletRequest));

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/diagram_type/default.jsp");
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_csDiagramSettingImageConfiguration =
			ConfigurableUtil.createConfigurable(
				CSDiagramSettingImageConfiguration.class, properties);
	}

	private String _getProductBaseURL(ThemeDisplay themeDisplay) {
		Layout layout = themeDisplay.getLayout();

		Group group = layout.getGroup();

		String siteBaseURL = HtmlUtil.escape(
			group.getDisplayURL(themeDisplay, layout.isPrivateLayout()));

		String productURLSeparator = _cpFriendlyURL.getProductURLSeparator(
			themeDisplay.getCompanyId());

		return siteBaseURL + productURLSeparator;
	}

	private Map<String, Object> _getProps(
			CSDiagramSetting csDiagramSetting,
			HttpServletRequest httpServletRequest)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		boolean admin = CPPortletKeys.CP_DEFINITIONS.equals(
			portletDisplay.getPortletName());

		HashMapBuilder.HashMapWrapper<String, Object> hashMapWrapper =
			HashMapBuilder.<String, Object>put(
				"datasetDisplayId", CSDiagramFDSNames.MAPPED_PRODUCTS
			).put(
				"diagramId", csDiagramSetting.getCSDiagramSettingId()
			).put(
				"imageURL",
				CSDiagramSettingUtil.getImageURL(csDiagramSetting, _dlURLHelper)
			).put(
				"isAdmin", admin
			).put(
				"pinsRadius", csDiagramSetting.getRadius()
			).put(
				"productId",
				() -> {
					CPDefinition cpDefinition =
						csDiagramSetting.getCPDefinition();

					return cpDefinition.getCProductId();
				}
			);

		if (!admin) {
			CommerceContext commerceContext =
				(CommerceContext)httpServletRequest.getAttribute(
					CommerceWebKeys.COMMERCE_CONTEXT);

			CommerceOrder commerceOrder = commerceContext.getCommerceOrder();

			if (commerceOrder != null) {
				hashMapWrapper.put(
					"cartId", commerceOrder.getCommerceOrderId());

				hashMapWrapper.put("orderUUID", commerceOrder.getUuid());
			}

			hashMapWrapper.put(
				"channelGroupId", commerceContext.getCommerceChannelGroupId());

			hashMapWrapper.put(
				"channelId", commerceContext.getCommerceChannelId());

			CommerceAccount commerceAccount =
				commerceContext.getCommerceAccount();

			if (commerceAccount != null) {
				hashMapWrapper.put(
					"commerceAccountId",
					commerceAccount.getCommerceAccountId());
			}

			CommerceCurrency commerceCurrency =
				commerceContext.getCommerceCurrency();

			hashMapWrapper.put(
				"commerceCurrencyCode", commerceCurrency.getCode());

			hashMapWrapper.put(
				"productBaseURL", _getProductBaseURL(themeDisplay));
		}

		return hashMapWrapper.build();
	}

	@Reference
	private CPFriendlyURL _cpFriendlyURL;

	private volatile CSDiagramSettingImageConfiguration
		_csDiagramSettingImageConfiguration;

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Language _languagea;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.shop.by.diagram.web)"
	)
	private ServletContext _servletContext;

}