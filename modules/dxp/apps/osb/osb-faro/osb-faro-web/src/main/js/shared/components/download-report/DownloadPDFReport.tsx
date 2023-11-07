import ClayForm, {ClayCheckbox} from '@clayui/form';
import React, {useMemo, useState} from 'react';
import {DownloadReportButton} from './DownloadReportButton';
import {DownloadReportModal} from './DownloadReportModal';
import {generateReport} from './utils';
import {sub} from 'shared/util/lang';
import {useModal} from '@clayui/modal';

export enum Containers {
	AcquisitionsCard = 'acquisitionsCardRoot',
	AssetAppearsOnCard = 'assetAppearsOnCardRoot',
	AudienceCard = 'audienceCardRoot',
	CohortAnalysisCard = 'cohortAnalysisCardRoot',
	DownloadsByLocationCard = 'downloadsByLocationCardRoot',
	DownloadsByTechnologyCard = 'downloadsByTechnologyCardRoot',
	InterestsCard = 'interestsCardRoot',
	SearchTermsCard = 'searchTermsCardRoot',
	SessionsByLocationCard = 'SessionsByLocationCardRoot',
	SessionTechnologyCard = 'SessionTechnologyCardRoot',
	SiteActivityCard = 'SiteActivityCardRoot',
	TopPagesCard = 'topPagesCardRoot',
	ViewsByLocationCard = 'viewsByLocationCardRoot',
	ViewsByTechnologyCard = 'viewsByTechnologyCardRoot',
	VisitorsBehaviorCard = 'visitorsBehaviorCardRoot',
	VisitorsByTimeCard = 'visitorsByTimeCardRoot'
}

export const CONTAINERS: {[key in Containers]: TContainer} = {
	[Containers.AcquisitionsCard]: {
		label: Liferay.Language.get('acquisitions'),
		layout: 2
	},
	[Containers.AssetAppearsOnCard]: {
		label: Liferay.Language.get('asset-appears-on'),
		layout: 1
	},
	[Containers.AudienceCard]: {
		label: Liferay.Language.get('audience'),
		layout: 1
	},
	[Containers.CohortAnalysisCard]: {
		label: Liferay.Language.get('cohort-analysis'),
		layout: 1
	},
	[Containers.DownloadsByLocationCard]: {
		label: Liferay.Language.get('downloads-by-location'),
		layout: 2
	},
	[Containers.DownloadsByTechnologyCard]: {
		label: Liferay.Language.get('downloads-by-technology'),
		layout: 2
	},
	[Containers.InterestsCard]: {
		label: Liferay.Language.get('interests'),
		layout: 3
	},
	[Containers.SearchTermsCard]: {
		label: Liferay.Language.get('search-terms'),
		layout: 3
	},
	[Containers.SessionsByLocationCard]: {
		label: Liferay.Language.get('sessions-by-location'),
		layout: 2
	},
	[Containers.SessionTechnologyCard]: {
		label: Liferay.Language.get('session-technology'),
		layout: 2
	},
	[Containers.SiteActivityCard]: {
		label: Liferay.Language.get('site-activity'),
		layout: 2
	},
	[Containers.TopPagesCard]: {
		label: Liferay.Language.get('top-pages'),
		layout: 2
	},
	[Containers.ViewsByLocationCard]: {
		label: Liferay.Language.get('views-by-location'),
		layout: 2
	},
	[Containers.ViewsByTechnologyCard]: {
		label: Liferay.Language.get('views-by-technology'),
		layout: 2
	},
	[Containers.VisitorsBehaviorCard]: {
		label: Liferay.Language.get('visitors-behavior'),
		layout: 2
	},
	[Containers.VisitorsByTimeCard]: {
		label: Liferay.Language.get('visitors-by-day-and-time'),
		layout: 3
	},
	[Containers.VisitorsBehaviorCard]: {
		label: Liferay.Language.get('visitors-behavior'),
		layout: 1
	}
};

export type TContainer = {label: string; layout: 1 | 2 | 3};
export type TransformedContainer = TContainer & {
	checked: boolean;
	id: Containers;
};

export interface IDownloadReport {
	disabled: boolean;
	containers: Containers[];
	subtitle: string;
	title: string;
	url?: string;
}

type ContainerList = {
	[key in Containers]: TransformedContainer;
};

const transformContainers = (containers: Containers[]): ContainerList =>
	containers.reduce((acc, id) => {
		acc[id] = {
			...CONTAINERS[id],
			checked: false,
			id
		};

		return acc;
	}, {} as ContainerList);

const DownloadPDFReport: React.FC<IDownloadReport> = ({
	containers: initialContainers,
	disabled,
	subtitle,
	title,
	url
}) => {
	const [loadingReport, setLoadingReport] = useState(false);
	const {observer, onOpenChange, open} = useModal();
	const [containers, setContainers] = useState<ContainerList>(() =>
		transformContainers(initialContainers)
	);

	const filteredContainers = useMemo(
		() => Object.values(containers).filter(({checked}) => checked),
		[containers]
	);

	return (
		<div className='download-report'>
			<DownloadReportButton
				disabled={disabled}
				loading={loadingReport}
				onClick={() => onOpenChange(true)}
			/>

			{open && (
				<DownloadReportModal
					alertMessage={
						sub(
							Liferay.Language.get(
								'the-x-file-is-being-generated-and-your-download-will-start-soon'
							),
							['PDF']
						) as string
					}
					descriptionMessage={Liferay.Language.get(
						'select-the-reports,-and-optionally-specify-the-date-range-to-generate-a-PDF-file-from-the-current-dashboard.-your-download-may-take-a-couple-of-minutes-to-process'
					)}
					disabled={!filteredContainers.length}
					infoMessage={Liferay.Language.get(
						'the-dashboard-will-be-downloaded-exactly-as-it-is-displayed-on-your-screen.-please-verify-if-the-desired-tabs-and-filters-are-selected-before-downloading'
					)}
					observer={observer}
					onClose={() => onOpenChange(false)}
					onSubmit={() => {
						setLoadingReport(true);

						generateReport({
							containers: filteredContainers,
							subtitle,
							title,
							url
						}).then(() => {
							setContainers(
								transformContainers(initialContainers)
							);
							setLoadingReport(false);
						});
					}}
				>
					<ClayForm.Group>
						<label>{Liferay.Language.get('select-reports')}</label>

						{Object.values(containers).map(({id, label}) => (
							<Checkbox
								key={id}
								label={label}
								onChange={newValue => {
									setContainers({
										...containers,
										[id]: {
											...containers[id],
											checked: newValue
										}
									});
								}}
							/>
						))}
					</ClayForm.Group>
				</DownloadReportModal>
			)}
		</div>
	);
};

export const Checkbox = ({label, onChange}) => {
	const [checked, setChecked] = useState(false);

	return (
		<ClayCheckbox
			checked={checked}
			label={label}
			onChange={() => {
				setChecked(!checked);
				onChange(!checked);
			}}
		/>
	);
};

export default DownloadPDFReport;
