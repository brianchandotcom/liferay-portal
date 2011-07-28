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

.portlet-staging-bar {
	.staging-tabview-list {
		border-color: #555;
		border-width: 0 0 1px;
		font-size: 1.2em;
		margin-bottom: 0;
	}

	.staging-icon-menu-container {
		display: inline-block;
		margin-right: -9px;
		margin-top: -0.1em;
		overflow: hidden;
		vertical-align: middle;

		a {
			display: block;
			float: right;
			min-height: 15px;
		}

		.staging-icon-menu.lfr-actions .lfr-trigger strong {
			min-width: 0;
			padding: 0;

			a {
				background-color: #555;
				background-image: url(<%= themeImagesPath %>/arrows/06_down.png);
				background-position: 50% 4px;
				padding: 0 14px 0 0;
			}
		}
	}

	.aui-tab-active {
		.staging-icon-menu.lfr-actions .lfr-trigger strong a {
			background-color: #EEE;
			background-image: url(<%= themeImagesPath %>/arrows/05_down.png);
		}
	}

	.branch-results {
		min-height: 300px;
	}

	.staging-bar {
		background-color: #888;

		.variations-tabview-list {
			background-color: #DDD;
		}

		.staging-tabview-content {
			border-bottom: 1px solid #636364;
			color: #EEE;
			padding: 0.5em 1em;

			.staging-icon {
				float: left;
				margin-right: 1em;
			}

			.layout-set-branch-info {
				font-size: 1.1em;
				margin-bottom: 1em;

				.layout-set-branch-description {
					border-right: 1px solid #AAA;
					padding: 0 0.5em 0;
					font-style: italic;
				}

				.layout-set-branch-pages {
					padding-left: 0.5em;
				}
			}

			.layout-info {
				.variations-tabview-list {
					margin-top: 1em;
				}

				.variations-tabview-content {
					overflow: hidden;

					.layout-branch-description {
						font-style: italic;
					}

					.manage-page-variations a {
						color: #EEE;
					}

					.layout-actions:after {
						clear: both;
						content: ".";
						display: block;
						height: 0;
						visibility: hidden;
					}

					.layout-revision-details {
						float: left;
					}

					.layout-revision-id {
						font-size: 0.6em;
					}
				}

				.layout-title {
					font-size: 1.2em;

					label {
						float: left;
					}

					.layout-breadcrumb {
						font-size: 1em;

						.breadcrumbs a {
							color: #EEE;
						}

						.breadcrumbs-horizontal {
							margin-bottom: 0;
							overflow: hidden;
							padding-left: 0.5em;

							li {
								background-image: url(<%= themeImagesPath %>/arrows/09_right.png);

								&.last {
									background-image: none;
									font-weight: bold;
									position: relative;
									text-decoration: none;
									top: -0.2em;
								}
							}
						}
					}
				}
			}

			.last-publication-variation-details {
				font-size: 0.8em;

				.layout-version {
					background: url(<%= themeImagesPath %>/common/pages.png) no-repeat 0 50%;
					padding: 2px 0 2px 20px;
				}

				.variation-name {
					background: url(<%= themeImagesPath %>/common/signal_instance.png) no-repeat 0 50%;
					margin-right: 10px;
					padding: 2px 0 2px 20px;
				}
			}

			.taglib-workflow-status {
				clear: left;
				color: #FFF;
				float: left;
				margin-right: 5em;

				.workflow-status, .workflow-version {
					color: #FFF;
				}

				.workflow-status-approved, .workflow-status-ready-for-publication {
					color: #8BEC59;
				}

				.workflow-status-draft {
					color: #CAD8F3;
				}

				.workflow-status-expired {
					color: #FF8E8E;
				}
			}

			.last-publication-branch, .staging-live-group-name {
				display: block;
				font-size: 1.3em;
				padding: 5px 0 0;
			}

			.layout-revision-toolbar {
				float: left;
				padding-left: 1em;
			}
		}
	}
}

.js .controls-hidden .staging-bar {
	display: none;
}