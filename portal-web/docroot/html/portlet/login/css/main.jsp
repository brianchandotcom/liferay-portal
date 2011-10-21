<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
--%>

<%@ include file="/html/portlet/css_init.jsp" %>

.portlet-login {
	.aui-form {
		fieldset {
			border-width: 0;
			margin-bottom: 0;
			padding: 0;
		}

		.aui-button-holder, .aui-form-column {
			margin-bottom: 10px;
		}
	}

	.facebook-login-button {
		.aui-button-content {
			background: url(<%= themeImagesPath %>/login/facebook_login_button.png) no-repeat scroll 0 50%;
			padding: 5px 5px 5px 30px;
		}

		.aui-button-input {
			background: transparent none;
			border-width: 0;
			color: #FFF;
			font-family: "lucida grande", Tahoma, Verdana, Arial, sans-serif;
			padding: 0;
			text-shadow: none;
		}
	}

	.navigation {
		background: #eee;
		border-top: 1px solid #BFBFBF;
		padding: 10px;
	}
}