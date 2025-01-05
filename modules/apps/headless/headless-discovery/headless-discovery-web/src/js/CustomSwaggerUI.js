/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useMemo} from 'react';
import {createRoot} from 'react-dom/client';
import SwaggerUI from 'swagger-ui-react';

import Icon from './Icon';

const CustomSwaggerUI = ({learnResources = [], ...swaggerProps}) => {
	const resourceMap = useMemo(() => {
		if (!Array.isArray(learnResources)) {
			return new Map();
		}

		return new Map(
			learnResources.map(({key, learnMessageDetails}) => [
				key,
				learnMessageDetails,
			])
		);
	}, [learnResources]);

	const createTooltipContent = useCallback(
		(key, container) => {
			const messageDetails = resourceMap.get(key)?.[0];
			if (!messageDetails) {
				return;
			}

			const tooltipContainer = document.createElement('div');
			tooltipContainer.className = 'tooltip-container';
			tooltipContainer.innerHTML = ` <span class="tooltip-icon">
										   		<div id="tooltip-icon-wrapper-${key}"></div>
										   </span>
										   <span class="tooltip-text">
										   		<div id="tooltip-content-${key}"></div>
										   </span>`;

			container.appendChild(tooltipContainer);

			const iconWrapper = tooltipContainer.querySelector(
				`#tooltip-icon-wrapper-${key}`
			);
			if (iconWrapper) {
				const iconRoot = createRoot(iconWrapper);
				iconRoot.render(<Icon symbol="question-circle" />);
			}

			const tooltipContent = tooltipContainer.querySelector(
				`#tooltip-content-${key}`
			);
			if (tooltipContent) {
				const messageRoot = createRoot(tooltipContent);
				messageRoot.render(
					<ClayLink
						href={messageDetails.url}
						rel="noopener noreferrer"
						target="_blank"
					>
						{messageDetails.message}
					</ClayLink>
				);
			}
		},
		[resourceMap]
	);

	const addTooltips = useCallback(() => {
		if (resourceMap.size === 0) {
			return;
		}

		resourceMap.forEach((_, key) => {
			const rows = document.querySelectorAll(
				`[data-param-name='${key}']`
			);

			rows.forEach((row) => {
				const inputContainer = row.querySelector(
					'td.parameters-col_description'
				);
				if (
					inputContainer &&
					!row.querySelector('.tooltip-container')
				) {
					createTooltipContent(key, inputContainer);
				}
			});
		});
	}, [resourceMap, createTooltipContent]);

	useEffect(() => {
		const swaggerContainer = document.getElementById('swagger-main');
		if (!swaggerContainer) {
			return;
		}

		const addTooltipsWithRAF = () => {
			requestAnimationFrame(addTooltips);
		};

		addTooltipsWithRAF();

		const observer = new MutationObserver(addTooltipsWithRAF);
		observer.observe(swaggerContainer, {
			childList: true,
			subtree: true,
		});

		return () => {
			observer.disconnect();
		};
	}, [addTooltips]);

	return (
		<div id="swagger-main">
			<SwaggerUI {...swaggerProps} />
		</div>
	);
};

CustomSwaggerUI.propTypes = {
	learnResources: PropTypes.arrayOf(
		PropTypes.shape({
			key: PropTypes.string.isRequired,
			learnMessageDetails: PropTypes.arrayOf(
				PropTypes.shape({
					languageId: PropTypes.string.isRequired,
					message: PropTypes.string.isRequired,
					url: PropTypes.string.isRequired,
				})
			).isRequired,
		})
	),
	swaggerProps: PropTypes.object,
};

export default React.memo(CustomSwaggerUI);
