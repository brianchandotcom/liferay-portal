/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayPanel from '@clayui/panel';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import LinkField from '../../../../../../app/components/fragment_configuration_fields/LinkField';
import {COMMON_STYLES_ROLES} from '../../../../../../app/config/constants/commonStylesRoles';
import {VIEWPORT_SIZES} from '../../../../../../app/config/constants/viewportSizes';
import {
	useDispatch,
	useSelector,
} from '../../../../../../app/contexts/StoreContext';
import updateItemConfig from '../../../../../../app/thunks/updateItemConfig';
import isMapped from '../../../../../../app/utils/editable_value/isMapped';
import {getEditableLinkValue} from '../../../../../../app/utils/getEditableLinkValue';
import {getResponsiveConfig} from '../../../../../../app/utils/getResponsiveConfig';
import {getLayoutDataItemPropTypes} from '../../../../../../prop_types/index';
import {CommonStyles} from './CommonStyles';
import ContainerDisplayOptions from './ContainerDisplayOptions';

export default function ContainerGeneralPanel({item}) {
	const dispatch = useDispatch();
	const languageId = useSelector((state) => state.languageId);

	const [linkConfig, setLinkConfig] = useState({});
	const [linkValue, setLinkValue] = useState({});

	useEffect(() => {
		setLinkConfig(item.config.link || {});
		setLinkValue(getEditableLinkValue(item.config.link, languageId));
	}, [item.config.link, languageId]);

	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const containerConfig = getResponsiveConfig(
		item.config,
		selectedViewportSize
	);

	const handleValueSelect = (_, nextLinkConfig) => {
		let nextConfig;

		if (isMapped(nextLinkConfig) || isMapped(linkConfig)) {
			nextConfig = {link: nextLinkConfig};
		}
		else {
			nextConfig = {
				link: linkConfig,
			};

			if (Object.keys(nextLinkConfig).length) {
				nextConfig = {
					link: {
						href: {
							...(linkConfig.href || {}),
							[languageId]: nextLinkConfig.href,
						},
						target: nextLinkConfig.target || '',
					},
				};
			}
		}

		dispatch(
			updateItemConfig({
				itemConfig: nextConfig,
				itemId: item.itemId,
			})
		);
	};

	return (
		<>
			{selectedViewportSize === VIEWPORT_SIZES.desktop && (
				<div className="mb-3 panel-group-sm">
					<ClayPanel
						collapsable
						defaultExpanded
						displayTitle={Liferay.Language.get('container-options')}
						displayType="unstyled"
						showCollapseIcon
					>
						<ClayPanel.Body>
							<LinkField
								field={{name: 'link'}}
								onValueSelect={handleValueSelect}
								value={linkValue || {}}
							/>

							<ContainerDisplayOptions item={item} />
						</ClayPanel.Body>
					</ClayPanel>
				</div>
			)}

			<CommonStyles
				commonStylesValues={containerConfig.styles}
				item={item}
				role={COMMON_STYLES_ROLES.general}
			/>
		</>
	);
}

ContainerGeneralPanel.propTypes = {
	item: getLayoutDataItemPropTypes({
		config: PropTypes.shape({
			link: PropTypes.oneOfType([
				PropTypes.shape({
					href: PropTypes.string,
					target: PropTypes.string,
				}),
				PropTypes.shape({
					classNameId: PropTypes.string,
					classPK: PropTypes.string,
					fieldId: PropTypes.string,
					target: PropTypes.string,
				}),
				PropTypes.shape({
					mappedField: PropTypes.string,
					target: PropTypes.string,
				}),
			]),
		}),
	}),
};
