/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayBadge from '@clayui/badge';
import ClayButton from '@clayui/button';
import ClayLayout from '@clayui/layout';
import ClayList from '@clayui/list';
import ClayPanel from '@clayui/panel';
import ClayProgressBar from '@clayui/progress-bar';
import classNames from 'classnames';
import {sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useContext, useEffect, useMemo, useState} from 'react';

import {SET_SELECTED_ITEM} from '../../constants/actionTypes';
import {
	StoreDispatchContext,
	StoreStateContext,
} from '../../context/StoreContext';
import getPageSpeedProgress from '../../utils/getPageSpeedProgress';
import NoIssuesLoaded from './NoIssuesLoaded';

export default function IssuesList() {
	const {data, error, languageId, loading} = useContext(StoreStateContext);

	const {imagesPath, layoutReportsIssues} = data;

	const [percentage, setPercentage] = useState(0);

	const localizedIssues = layoutReportsIssues?.[languageId];

	useEffect(() => {
		if (loading && !error) {
			const initial = Date.now();
			const interval = setInterval(() => {
				const elapsedTimeInSeconds = (Date.now() - initial) / 1000;
				const progress = getPageSpeedProgress(elapsedTimeInSeconds);

				setPercentage(Math.trunc(progress));
			}, 500);

			return () => {
				clearInterval(interval);
				setPercentage(0);
			};
		}
	}, [error, loading]);

	const successImage = `${imagesPath}/issues_success.gif`;

	return (
		<>
			{!Liferay.FeatureFlags['LPS-187284'] &&
				localizedIssues &&
				!loading && (
					<ClayAlert
						className="c-mb-4"
						displayType="info"
						variant="stripe"
					>
						{sub(
							Liferay.Language.get(
								'showing-data-from-x-relaunch-to-update-data'
							),
							localizedIssues.date
						)}
					</ClayAlert>
				)}
			<div
				className={classNames('c-pb-3', {
					'c-px-3': !Liferay.FeatureFlags['LPS-187284'],
				})}
			>
				{loading ? (
					<LoadingProgressBar percentage={percentage} />
				) : localizedIssues ? (
					<Issues
						layoutReportsIssues={localizedIssues.issues}
						successImage={successImage}
					/>
				) : (
					<NoIssuesLoaded />
				)}
			</div>
		</>
	);
}

const LoadingProgressBar = ({percentage}) => (
	<div className="c-my-4 text-secondary">
		{Liferay.Language.get('connecting-with-google-pagespeed')}

		<ClayProgressBar value={percentage} />
	</div>
);

LoadingProgressBar.propTypes = {
	percentage: PropTypes.number.isRequired,
};

const Issues = ({layoutReportsIssues, successImage}) => {
	const hasIssues = useMemo(() => {
		return layoutReportsIssues?.some(({total}) => total > 0);
	}, [layoutReportsIssues]);

	return (
		<div className="c-my-4">
			{!hasIssues && (
				<div className="c-pb-5 text-center">
					<img
						alt={Liferay.Language.get(
							'success-page-audit-image-alt-description'
						)}
						className="c-my-4"
						src={successImage}
						width="120px"
					/>

					<div className="font-weight-semi-bold">
						<span>
							{Liferay.Language.get('your-page-has-no-issues')}
						</span>
					</div>
				</div>
			)}

			<ClayPanel.Group className="panel-group-flush panel-group-sm">
				{layoutReportsIssues?.map((section) => (
					<Section key={section.key} section={section} />
				))}
			</ClayPanel.Group>
		</div>
	);
};

Issues.propTypes = {
	layoutReportsIssues: PropTypes.array.isRequired,
	successImage: PropTypes.string.isRequired,
};

const Section = ({section}) => {
	let sectionTotal = section.total;

	if (sectionTotal > 100) {
		sectionTotal = '+100';
	}

	return (
		<ClayPanel
			collapsable
			defaultExpanded={sectionTotal > 0}
			displayTitle={
				<span className="c-inner" tabIndex="-1">
					<ClayPanel.Title>
						<ClayLayout.ContentRow>
							<ClayLayout.ContentCol
								className="align-self-center panel-title"
								expand
							>
								{section.title}
							</ClayLayout.ContentCol>

							<ClayLayout.ContentCol>
								<ClayBadge
									displayType={
										sectionTotal === 0 ? 'success' : 'info'
									}
									label={sectionTotal}
								/>
							</ClayLayout.ContentCol>
						</ClayLayout.ContentRow>
					</ClayPanel.Title>
				</span>
			}
			displayType="unstyled"
			showCollapseIcon={true}
		>
			<ClayPanel.Body>
				{sectionTotal === '0' ? (
					<div className="text-secondary">
						{sub(
							Liferay.Language.get(
								'there-are-no-x-related-issues'
							),
							section.title
						)}
					</div>
				) : (
					<ClayList className="c-m-0">
						{section.details.map((issue) => (
							<Issue issue={issue} key={issue.key} />
						))}
					</ClayList>
				)}
			</ClayPanel.Body>
		</ClayPanel>
	);
};

Section.propTypes = {
	section: PropTypes.object.isRequired,
};

const Issue = ({issue}) => {
	let issueTotal = issue.total;

	if (issueTotal > 100) {
		issueTotal = '+100';
	}

	const dispatch = useContext(StoreDispatchContext);

	return (
		issueTotal > 0 && (
			<ClayButton
				displayType="unstyled"
				onClick={() =>
					dispatch({
						item: {...issue, type: 'issue'},
						type: SET_SELECTED_ITEM,
					})
				}
			>
				<span
					className="align-items-center c-inner c-pb-3 d-flex justify-content-between text-secondary"
					tabIndex="-1"
				>
					{issue.title}

					<ClayBadge
						displayType={issueTotal === 0 ? 'success' : 'info'}
						label={issueTotal}
					/>
				</span>
			</ClayButton>
		)
	);
};

Issue.propTypes = {
	issue: PropTypes.object.isRequired,
};
