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

const portlet = {
	data: {
		pageRenderState: {
			encodedCurrentURL: 'http%3A%2F%2Flocalhost%3A8080%2F',
			portlets: {
				PortletA: {
					allowedPM: ['view'],
					allowedWS: [
						'exclusive',
						'maximized',
						'minimized',
						'normal',
						'pop_up'
					],
					encodedActionURL:
						'http%3A%2F%2Flocalhost%3A8080%2Fweb%2Fguest%2Fv3.0-parameter-tests%3Fp_p_id%3Dcom_liferay_portal_search_web_portlet_SearchPortlet_INSTANCE_templateSearch%26p_p_lifecycle%3D1%26p_auth%3DXcc0kyXi',
					encodedRenderURL:
						'http%3A%2F%2Flocalhost%3A8080%2Fweb%2Fguest%2Fv3.0-parameter-tests%3Fp_p_id%3Dcom_liferay_portal_search_web_portlet_SearchPortlet_INSTANCE_templateSearch%26p_p_lifecycle%3D0',
					encodedResourceURL:
						'http%3A%2F%2Flocalhost%3A8080%2Fweb%2Fguest%2Fv3.0-parameter-tests%3Fp_p_id%3Dcom_liferay_portal_search_web_portlet_SearchPortlet_INSTANCE_templateSearch%26p_p_lifecycle%3D2',
					pubParms: {},
					renderData: {
						content: '',
						mimeType: ''
					},
					state: {
						parameters: {},
						portletMode: 'view',
						windowState: 'normal'
					}
				},
				PortletB: {
					allowedPM: ['view'],
					allowedWS: [
						'exclusive',
						'maximized',
						'minimized',
						'normal',
						'pop_up'
					],
					encodedActionURL:
						'http%3A%2F%2Flocalhost%3A8080%2Fweb%2Fguest%2Fv3.0-parameter-tests%3Fp_p_id%3DParamTestPortlet_WAR_PortletHubDemo%26p_p_lifecycle%3D1%26p_auth%3DXcc0kyXi',
					encodedRenderURL:
						'http%3A%2F%2Flocalhost%3A8080%2Fweb%2Fguest%2Fv3.0-parameter-tests%3Fp_p_id%3DParamTestPortlet_WAR_PortletHubDemo%26p_p_lifecycle%3D0',
					encodedResourceURL:
						'http%3A%2F%2Flocalhost%3A8080%2Fweb%2Fguest%2Fv3.0-parameter-tests%3Fp_p_id%3DParamTestPortlet_WAR_PortletHubDemo%26p_p_lifecycle%3D2',
					pubParms: {
						color:
							'p_r_p_rp_http://www.apache.org/portals/pluto/ResourcePortlet_color',
						imgName:
							'p_r_p_rp_http://www.apache.org/portals/pluto/ResourcePortlet_imgName'
					},
					renderData: {
						content: '',
						mimeType: ''
					},
					state: {
						parameters: {},
						portletMode: 'view',
						windowState: 'normal'
					}
				},
				PortletC: {
					allowedPM: ['view'],
					allowedWS: [
						'exclusive',
						'maximized',
						'minimized',
						'normal',
						'pop_up'
					],
					encodedActionURL:
						'http%3A%2F%2Flocalhost%3A8080%2Fweb%2Fguest%2Fv3.0-parameter-tests%3Fp_p_id%3Dcom_liferay_chat_web_portlet_ChatPortlet%26p_p_lifecycle%3D1%26p_auth%3DXcc0kyXi',
					encodedRenderURL:
						'http%3A%2F%2Flocalhost%3A8080%2Fweb%2Fguest%2Fv3.0-parameter-tests%3Fp_p_id%3Dcom_liferay_chat_web_portlet_ChatPortlet%26p_p_lifecycle%3D0',
					encodedResourceURL:
						'http%3A%2F%2Flocalhost%3A8080%2Fweb%2Fguest%2Fv3.0-parameter-tests%3Fp_p_id%3Dcom_liferay_chat_web_portlet_ChatPortlet%26p_p_lifecycle%3D2',
					pubParms: {},
					renderData: {
						content: '',
						mimeType: ''
					},
					state: {
						parameters: {},
						portletMode: 'view',
						windowState: 'normal'
					}
				},
				PortletD: {
					allowedPM: ['view'],
					allowedWS: [
						'exclusive',
						'maximized',
						'minimized',
						'normal',
						'pop_up'
					],
					encodedActionURL:
						'http%3A%2F%2Flocalhost%3A8080%2Fweb%2Fguest%2Fv3.0-parameter-tests%3Fp_p_id%3Dcom_liferay_portal_search_web_portlet_SearchPortlet%26p_p_lifecycle%3D1%26p_auth%3DXcc0kyXi',
					encodedRenderURL:
						'http%3A%2F%2Flocalhost%3A8080%2Fweb%2Fguest%2Fv3.0-parameter-tests%3Fp_p_id%3Dcom_liferay_portal_search_web_portlet_SearchPortlet%26p_p_lifecycle%3D0',
					encodedResourceURL:
						'http%3A%2F%2Flocalhost%3A8080%2Fweb%2Fguest%2Fv3.0-parameter-tests%3Fp_p_id%3Dcom_liferay_portal_search_web_portlet_SearchPortlet%26p_p_lifecycle%3D2',
					pubParms: {},
					renderData: {
						content: 'PortletD content',
						mimeType: ''
					},
					state: {
						parameters: {},
						portletMode: 'view',
						windowState: 'normal'
					}
				},
				PortletE: {
					allowedPM: ['view'],
					allowedWS: [
						'exclusive',
						'maximized',
						'minimized',
						'normal',
						'pop_up'
					],
					encodedActionURL:
						'http%3A%2F%2Flocalhost%3A8080%2Fweb%2Fguest%2Fv3.0-parameter-tests%3Fp_p_id%3Dcom_liferay_site_navigation_menu_web_portlet_SiteNavigationMenuPortlet%26p_p_lifecycle%3D1%26p_auth%3DXcc0kyXi',
					encodedRenderURL:
						'http%3A%2F%2Flocalhost%3A8080%2Fweb%2Fguest%2Fv3.0-parameter-tests%3Fp_p_id%3Dcom_liferay_site_navigation_menu_web_portlet_SiteNavigationMenuPortlet%26p_p_lifecycle%3D0',
					encodedResourceURL:
						'http%3A%2F%2Flocalhost%3A8080%2Fweb%2Fguest%2Fv3.0-parameter-tests%3Fp_p_id%3Dcom_liferay_site_navigation_menu_web_portlet_SiteNavigationMenuPortlet%26p_p_lifecycle%3D2',
					pubParms: {},
					renderData: {
						content: '',
						mimeType: ''
					},
					state: {
						parameters: {},
						portletMode: 'view',
						windowState: 'normal'
					}
				},
				PortletF: {
					allowedPM: ['view'],
					allowedWS: [
						'exclusive',
						'maximized',
						'minimized',
						'normal',
						'pop_up'
					],
					encodedActionURL:
						'http%3A%2F%2Flocalhost%3A8080%2Fweb%2Fguest%2Fv3.0-parameter-tests%3Fp_p_id%3Dcom_liferay_product_navigation_product_menu_web_portlet_ProductMenuPortlet%26p_p_lifecycle%3D1%26p_auth%3DXcc0kyXi',
					encodedRenderURL:
						'http%3A%2F%2Flocalhost%3A8080%2Fweb%2Fguest%2Fv3.0-parameter-tests%3Fp_p_id%3Dcom_liferay_product_navigation_product_menu_web_portlet_ProductMenuPortlet%26p_p_lifecycle%3D0',
					encodedResourceURL:
						'http%3A%2F%2Flocalhost%3A8080%2Fweb%2Fguest%2Fv3.0-parameter-tests%3Fp_p_id%3Dcom_liferay_product_navigation_product_menu_web_portlet_ProductMenuPortlet%26p_p_lifecycle%3D2',
					pubParms: {
						layoutSetBranchId: 'p_r_p_layoutSetBranchId',
						privateLayout: 'p_r_p_privateLayout',
						selPlid: 'p_r_p_selPlid'
					},
					renderData: {
						content: '',
						mimeType: ''
					},
					state: {
						parameters: {},
						portletMode: 'view',
						windowState: 'normal'
					}
				},
				PortletG: {
					allowedPM: ['view'],
					allowedWS: [
						'exclusive',
						'maximized',
						'minimized',
						'normal',
						'pop_up'
					],
					encodedActionURL:
						'http%3A%2F%2Flocalhost%3A8080%2Fweb%2Fguest%2Fv3.0-parameter-tests%3Fp_p_id%3Dcom_liferay_product_navigation_user_personal_bar_web_portlet_ProductNavigationUserPersonalBarPortlet%26p_p_lifecycle%3D1%26p_auth%3DXcc0kyXi',
					encodedRenderURL:
						'http%3A%2F%2Flocalhost%3A8080%2Fweb%2Fguest%2Fv3.0-parameter-tests%3Fp_p_id%3Dcom_liferay_product_navigation_user_personal_bar_web_portlet_ProductNavigationUserPersonalBarPortlet%26p_p_lifecycle%3D0',
					encodedResourceURL:
						'http%3A%2F%2Flocalhost%3A8080%2Fweb%2Fguest%2Fv3.0-parameter-tests%3Fp_p_id%3Dcom_liferay_product_navigation_user_personal_bar_web_portlet_ProductNavigationUserPersonalBarPortlet%26p_p_lifecycle%3D2',
					renderData: {
						content: '',
						mimeType: ''
					},
					state: {
						parameters: {},
						portletMode: 'view',
						windowState: 'normal'
					}
				}
			},
			prpMap: {
				PortletA: ['PortletA|something|PortletB|color'],
				PortletB: ['PortletA|itemsPerPage|PortletB|color'],
				PortletC: ['PortletD|privateLayout|PortletA|something'],
				PortletD: ['PortletC|imgName|PortletA|something'],
				PortletF: ['PortletB|setLayout|PortletA|something']
			}
		}
	},

	getIds() {
		return Object.keys(portlet.data.pageRenderState.portlets);
	},

	resource: {
		getCacheability(url) {
			const regex = /p_p_cacheability=(\w+)/g;
			const str = regex.exec(url);

			let cacheability = '';

			if (Array.isArray(str) && str.length === 2) {
				cacheability = str[1];
			}

			return cacheability;
		},

		isResourceUrl(url) {
			const regex = /(p_p_lifecycle=2)(&p_p_resource_id=\w+)?/g;

			const str = regex.exec(url);

			let found = false;

			if (Array.isArray(str) && str.length > 0) {
				found = true;
			}

			return found;
		}
	}
};

global.portlet = portlet;
