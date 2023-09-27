/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import ClayLink from '@clayui/link';
import PropTypes from 'prop-types';
import React, {useMemo} from 'react';

const ItemLanguages = ({urls}) => {
	const sortedViewURLS = useMemo(
		() =>
			urls
				.sort((a, b) => {
					if (a.languageId < b.languageId) {
						return -1;
					}

					if (a.languageId > b.languageId) {
						return 1;
					}

					return 0;
				})
				.sort((a) => {
					if (a.default) {
						return -1;
					}

					return 0;
				}),
		[urls]
	);

	return (
		<div className="c-mb-4 sidebar-section">
			<h5 className="font-weight-semi-bold">
				{Liferay.Language.get('languages-translated-into')}
			</h5>

			{sortedViewURLS.map((language) => (
				<ClayLayout.ContentRow
					key={language.languageId}
					verticalAlign="center"
				>
					<ClayLayout.ContentCol className="inline-item-before">
						<ClayIcon
							className="c-mt-1"
							symbol={language.languageId.toLowerCase()}
						/>
					</ClayLayout.ContentCol>

					<ClayLayout.ContentCol expand={!!language.viewURL}>
						<ClayLayout.ContentRow
							key={language.languageId}
							verticalAlign="center"
						>
							<ClayLayout.ContentCol className="inline-item-before small">
								{language.languageId}
							</ClayLayout.ContentCol>

							<ClayLayout.ContentCol>
								{language.default && (
									<ClayLabel
										className="d-inline"
										displayType="info"
									>
										{Liferay.Language.get('default')}
									</ClayLabel>
								)}
							</ClayLayout.ContentCol>
						</ClayLayout.ContentRow>
					</ClayLayout.ContentCol>

					{language.viewURL && (
						<ClayLayout.ContentCol>
							<ClayLink
								borderless
								data-tooltip-align="top"
								displayType="secondary"
								href={language.viewURL}
								monospaced
								outline
								title={Liferay.Language.get('view')}
							>
								<ClayIcon symbol="view" />
							</ClayLink>
						</ClayLayout.ContentCol>
					)}
				</ClayLayout.ContentRow>
			))}
		</div>
	);
};

ItemLanguages.propTypes = {
	urls: PropTypes.array.isRequired,
};

export default ItemLanguages;
