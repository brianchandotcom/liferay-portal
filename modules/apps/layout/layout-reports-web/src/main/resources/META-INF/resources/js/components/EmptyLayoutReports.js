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

import ClayLink from '@clayui/link';
import PropTypes from 'prop-types';
import React from 'react';

export default function EmptyLayoutReports({
	assetsPath,
	configurePageSpeedURL,
}) {
	const defaultIllustration = `${assetsPath}/issues-default.svg`;

	return (
		<div className="text-center">
			<img
				alt={Liferay.Language.get(
					'default-page-audit-image-alt-description'
				)}
				className="c-mb-4 c-mt-5"
				src={defaultIllustration}
				width="120px"
			/>

			<div className="c-mb-2 font-weight-semi-bold">
				<span>
					{Liferay.Language.get(
						"check-issues-that-impact-on-your-page's-accessibility-and-seo"
					)}
				</span>
			</div>

			{configurePageSpeedURL ? (
				<>
					<div className="c-mb-3 text-secondary">
						{Liferay.Language.get(
							'connect-to-pagespeed-to-run-a-page-audit'
						)}
					</div>

					<ClayLink
						className="btn btn-secondary"
						href={configurePageSpeedURL}
					>
						{Liferay.Language.get('connect-to-pagespeed')}
					</ClayLink>
				</>
			) : (
				<div className="text-secondary">
					<span>
						{Liferay.Language.get(
							'connect-with-google-pagespeed-from-site-settings-pages-google-pagespeed'
						)}
					</span>
				</div>
			)}
		</div>
	);
}

EmptyLayoutReports.propTypes = {
	assetsPath: PropTypes.string.isRequired,
	configurePageSpeedURL: PropTypes.string,
};
