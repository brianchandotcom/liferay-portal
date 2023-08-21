/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayLabel from '@clayui/label';
import classNames from 'classnames';
import {sub} from 'frontend-js-web';
import React, {
	Dispatch,
	SetStateAction,
	useEffect,
	useMemo,
	useState,
} from 'react';

import {
	Entries,
	FILTER_NAMES,
	Fragment,
	FragmentFilter,
} from '../../constants/Fragment';

interface PropsResultsBar {
	className: string;
	filters: FragmentFilter;
	fragments: Fragment[];
	onSetFilters: Dispatch<SetStateAction<FragmentFilter>>;
}

interface PropsResultsBarItemLabel {
	filterKey: keyof FragmentFilter;
	label: string;
	onSetFeedback: Dispatch<SetStateAction<string>>;
	onSetFilters: Dispatch<SetStateAction<FragmentFilter>>;
}

const ResultsBarItem = ({
	children,
	expand,
}: {
	children: React.ReactElement;
	expand?: boolean;
}) => {
	return (
		<div className={classNames('tbar-item', {'tbar-item-expand': expand})}>
			{children}
		</div>
	);
};

const ResultsBarItemLabel = ({
	filterKey,
	label,
	onSetFeedback,
	onSetFilters,
}: PropsResultsBarItemLabel) => {
	return (
		<ResultsBarItem>
			<ClayLabel
				className="component-label tbar-label"
				closeButtonProps={{
					['aria-label']: sub(
						Liferay.Language.get('remove-x-filter'),
						label
					),
					onClick: () => {
						onSetFilters((filters) => ({
							...filters,
							[filterKey]: null,
						}));
						onSetFeedback(Liferay.Language.get('filter-removed'));
					},
				}}
				displayType="unstyled"
				withClose
			>
				{label}
			</ClayLabel>
		</ResultsBarItem>
	);
};

export default function ResultsBar({
	className,
	filters,
	fragments,
	onSetFilters,
}: PropsResultsBar) {
	const [feedback, setFeedback] = useState('');

	useEffect(() => {
		if (feedback) {
			const timeout = setTimeout(() => setFeedback(''), 1000);

			return () => clearTimeout(timeout);
		}
	}, [feedback]);

	const hasActiveFilters = useMemo(
		() => Object.entries(filters).some(([, value]) => value),
		[filters]
	);

	return (
		<>
			<span className="sr-only" role="alert">
				{feedback}
			</span>

			{hasActiveFilters ? (
				<div
					className={classNames(
						'subnav-tbar subnav-tbar-primary',
						className
					)}
				>
					<div className="tbar-nav tbar-nav-wrap">
						<ResultsBarItem>
							<span className="component-text">
								{sub(
									Liferay.Language.get('x-results-for'),
									fragments.length
								)}
							</span>
						</ResultsBarItem>

						{(Object.entries(filters) as Entries<
							FragmentFilter
						>).map(([key, value]) =>
							value ? (
								<ResultsBarItemLabel
									filterKey={key}
									key={key}
									label={FILTER_NAMES[value]}
									onSetFeedback={setFeedback}
									onSetFilters={onSetFilters}
								/>
							) : null
						)}

						<ResultsBarItem expand>
							<ClayButton
								aria-label={Liferay.Language.get(
									'clear-filters'
								)}
								className="ml-auto"
								displayType={null}
								onClick={() => {
									onSetFilters({
										origin: null,
										status: null,
										type: null,
									});
									setFeedback(
										Liferay.Language.get('filters-cleared')
									);
								}}
							>
								{Liferay.Language.get('clear')}
							</ClayButton>
						</ResultsBarItem>
					</div>
				</div>
			) : null}
		</>
	);
}
