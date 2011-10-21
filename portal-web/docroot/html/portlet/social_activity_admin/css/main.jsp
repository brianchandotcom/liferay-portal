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

.portlet-social-activity-admin h4 {
	font-size: 1.2em;
	margin-bottom: 10px;
}

.social-activity-items {
	float: left;
	width: 220px;

	h4 {
		font-size: 1.1em;
		margin-bottom: 0px;

		.field-input-choice {
			margin-right: 0.7em;
			margin-top: 0.2em;
		}
	}

	.aui-field-choice {
		display: inline-block;
	}

	.social-activity-item {
		cursor: pointer;
		padding: 6px 0px 6px 3px;
		position: relative;

		a {
			color: #000;
			text-decoration:none;
		}

		.settings-label {
			padding: 7px 12px;
		}
	}

	.social-activity-item:hover {
		background-color: #CDE5FD;
	}

	.social-activity-item.selected {
		background-color: #83BFFA;
	}

	.social-activity-item.selected a:after {
		border: 15px solid #83BFFA;
		border-color: transparent transparent transparent #83BFFA;
		content: "";
		display: block;
		margin-top: -15px;
		position: absolute;
		right: -30px;
		top: 50%;
		z-index: 2;
	}
}

.social-activity-item-content {
	float: left;
	width: 80%;

	.settings-container {
		.aui-settings-container-content {
			background-color: #FFF;
			border: 1px solid #CCC;
			margin: 0px 15px 5px 0px;
			padding: 0px 15px;

			.aui-settings-field {
				font-weight: bold;
				list-style: none;

				.aui-settings-field-content {
					.field-values {
						display: inline-block;
						width: 750px;
					}

					select {
						margin: 0px 4px;
						padding: 3px;
					}
				}

				.field-text {
					font-weight: normal;
				}
			}

			.container-drop-box {
				margin: 0;
				min-height: 130px;

				.content-field {
					border-bottom: 1px solid #ccc;
					padding: 8px 10px;
				}
			}

			.settings-button-holder {
				padding: 5px 10px;
			}

			.settings-header-holder {
				border-bottom: 1px solid #ccc;

				.complementary-element {
					float: left;
					font-weight: bold;
					list-style: none;

					div {
						padding: 10px 0;
					}
				}

				.settings-header {
					display: inline-block;
					height: 36px;
					margin: 0px;

					.action-field {
						-moz-border-radius: 5px;
						-webkit-border-radius: 5px;
						background-color: #D9D9D9;
						border-radius: 5px;
						cursor: pointer;
						float: left;
						margin: 5px;
						padding: 5px 5px;
					}
				}

				.settings-header-label {
					float: left;
					font-weight: bold;
					padding: 10px;
				}
			}

			.settings-field-buttons {
				display: inline-block;
				height: 5px;

				.settings-button {
					float: left;
				}

				.settings-icon-minimize {
					background-image: url(<%= themeImagesPath %>/portlet/minimize.png);
					background-repeat: no-repeat;
					clip: rect(0pt, 0pt, 0pt, 0pt);
					height: 16px;
					margin-top: -8px;
					padding: 0;
					right: 0;
					width: 16px;
				}

				.settings-icon-close {
					background-image: url(<%= themeImagesPath %>/portlet/close.png);
					background-repeat: no-repeat;
					clip: rect(0pt, 0pt, 0pt, 0pt);
					height: 16px;
					margin-top: -8px;
					padding: 0;
					right: 0;
					width: 16px;
				}

				.settings-icon-maximize {
					background-image: url(<%= themeImagesPath %>/portlet/maximize.png);
					background-repeat: no-repeat;
					clip: rect(0pt, 0pt, 0pt, 0pt);
					height: 16px;
					margin-top: -8px;
					padding: 0;
					right: 0;
					width: 16px;
				}
			}

			.settings-limit {
				margin-left: 170px;
				margin-top: 5px;

				.settings-limit-field {
					font-weight: normal;
					list-style: none;

					td {
						padding: 1px;
					}

					.field-text {
						display: table-cell;
					}
				}
			}
		}

		.settings-label {
			display: inline-block;
			padding-right: 3px;
			text-align: right;
			width: 190px;
		}
	}
}