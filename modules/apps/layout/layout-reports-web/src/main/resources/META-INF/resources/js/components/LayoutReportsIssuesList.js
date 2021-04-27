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

import ClayBadge from '@clayui/badge';
import ClayLayout from '@clayui/layout';
import ClayPanel from '@clayui/panel';
import PropTypes from 'prop-types';
import React, {useContext, useMemo} from 'react';

import {StoreStateContext} from '../context/StoreContext';

export default function LayoutReportsIssuesList() {
	const {data} = useContext(StoreStateContext);

	const {assetsPath, layoutReportsIssues} = data;

	const hasIssues = useMemo(() => {
		return layoutReportsIssues.some(({total}) => total > 0);
	}, [layoutReportsIssues]);

	const successIllustration = `${assetsPath}/issues-success.gif`;

	return (
		<div className="c-my-4">
			{!hasIssues && (
				<div className="text-center">
					<img
						alt={Liferay.Language.get(
							'success-page-audit-image-alt-description'
						)}
						className="c-my-4"
						src={successIllustration}
						width="120px"
					/>

					<div className="font-weight-semi-bold">
						<span>{Liferay.Language.get('congratulations')}</span>
					</div>
					<div className="c-mb-5 c-mt-2 text-secondary">
						{Liferay.Language.get(
							'your-page-does-not-have-any-issues-to-fix'
						)}
					</div>
				</div>
			)}

			<ClayPanel.Group className="panel-group-flush panel-group-sm">
				{layoutReportsIssues?.map(({key, title, total}) => {
					let displayValue = total;

					if (total > 100) {
						displayValue = '+100';
					}

					return (
						<ClayPanel
							collapsable
							collapseClassNames="c-mb-4 c-mt-3"
							displayTitle={
								<span className="c-inner" tabIndex="-1">
									<ClayPanel.Title>
										<ClayLayout.ContentRow>
											<ClayLayout.ContentCol
												className="align-self-center panel-title"
												expand
											>
												{title}
											</ClayLayout.ContentCol>
											<ClayLayout.ContentCol>
												<ClayBadge
													displayType={
														total === 0
															? 'success'
															: 'info'
													}
													label={displayValue}
												/>
											</ClayLayout.ContentCol>
										</ClayLayout.ContentRow>
									</ClayPanel.Title>
								</span>
							}
							displayType="unstyled"
							key={key}
							showCollapseIcon={true}
						>
							<ClayPanel.Body>
								{displayValue === '0' && (
									<div className="text-secondary">
										{Liferay.Util.sub(
											Liferay.Language.get(
												'there-are-no-x-related-issues'
											),
											title
										)}
									</div>
								)}
							</ClayPanel.Body>
						</ClayPanel>
					);
				})}
			</ClayPanel.Group>
		</div>
	);
}

LayoutReportsIssuesList.propTypes = {
	layoutReportsIssues: PropTypes.arrayOf(
		PropTypes.shape({
			key: PropTypes.string.isRequired,
			title: PropTypes.string.isRequired,
			total: PropTypes.string.isRequired,
		})
	),
};
