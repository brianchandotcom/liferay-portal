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

import ClayAlert from '@clayui/alert';
import ClayBadge from '@clayui/badge';
import ClayButton from '@clayui/button';
import ClayLayout from '@clayui/layout';
import ClayList from '@clayui/list';
import ClayPanel from '@clayui/panel';
import ClayProgressBar from '@clayui/progress-bar';
import PropTypes from 'prop-types';
import React, {useContext, useEffect, useMemo, useState} from 'react';

import {SET_SELECTED_ISSUE} from '../constants/actionTypes';
import {StoreDispatchContext, StoreStateContext} from '../context/StoreContext';
import getPageSpeedProgress from '../utils/getPageSpeedProgress';
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
			{localizedIssues && !loading && (
				<ClayAlert className="mb-4" displayType="info" variant="stripe">
					{Liferay.Util.sub(
						Liferay.Language.get(
							'showing-data-from-x-relaunch-to-update-data'
						),
						localizedIssues.date
					)}
				</ClayAlert>
			)}
			<div className="pb-3 px-3">
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
	<div className="my-4 text-secondary">
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
		<div className="my-4">
			{!hasIssues && (
				<div className="pb-5 text-center">
					<img
						alt={Liferay.Language.get(
							'success-page-audit-image-alt-description'
						)}
						className="my-4"
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
						{Liferay.Util.sub(
							Liferay.Language.get(
								'there-are-no-x-related-issues'
							),
							section.title
						)}
					</div>
				) : (
					<ClayList>
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
			<ClayList.Item action className="border-0 issue rounded-0" flex>
				<ClayButton
					className="w-100"
					displayType="unstyled"
					onClick={() => dispatch({issue, type: SET_SELECTED_ISSUE})}
				>
					<span
						className="align-items-center c-inner d-flex justify-content-between m-0 px-2 text-secondary w-100"
						tabIndex="-1"
					>
						{issue.title}
						<ClayBadge
							displayType={issueTotal === 0 ? 'success' : 'info'}
							label={issueTotal}
						/>
					</span>
				</ClayButton>
			</ClayList.Item>
		)
	);
};

Issue.propTypes = {
	issue: PropTypes.object.isRequired,
};
