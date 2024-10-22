import ClayForm, {ClayCheckbox} from '@clayui/form';
import html2canvas from 'html2canvas';
import React, {useEffect, useMemo, useState} from 'react';
import {addAlert} from 'shared/actions/alerts';
import {Alert} from 'shared/types';
import {DownloadReportButton} from './DownloadReportButton';
import {DownloadReportModal} from './DownloadReportModal';
import {
	JSPDFExtension,
	JSPDFExtensionContainer,
	PosX,
	Size,
	Weight
} from './jsPDF';
import {sub} from 'shared/util/lang';
import {Text} from '@clayui/core';
import {useDispatch} from 'react-redux';
import {useDownloadReportContext} from './DownloadReportContext';
import {useModal} from '@clayui/modal';

export enum ReportContainer {
	AcquisitionsCard = 'container.report.acquisitionsCard',
	ActiveIndividualsCard = 'container.report.activeIndividualsCard',
	AssetAppearsOnCard = 'container.report.assetAppearsOnCard',
	AudienceCard = 'container.report.audienceCard',
	CohortAnalysisCard = 'container.report.cohortAnalysisCard',
	CurrentTotalsCard = 'container.report.currentTotalsCard',
	DistributionBreakdownCard = 'container.report.distributionBreakdownCard',
	DownloadsByLocationCard = 'container.report.downloadsByLocationCard',
	DownloadsByTechnologyCard = 'container.report.downloadsByTechnologyCard',
	EnrichedProfilesCard = 'container.report.enrichedProfilesCard',
	InterestsCard = 'container.report.interestsCard',
	SearchTermsCard = 'container.report.searchTermsCard',
	SegmentCompositionCard = 'container.report.segmentCompositionCard',
	SegmentCriteriaCard = 'container.report.segmentCriteriaCard',
	SegmentMembershipCard = 'container.report.segmentMembershipCard',
	SessionsByLocationCard = 'container.report.sessionsByLocationCard',
	SessionTechnologyCard = 'container.report.sessionTechnologyCard',
	SiteActivityCard = 'container.report.siteActivityCard',
	SubmissionsByLocationCard = 'container.report.submissionsByLocationCard',
	SubmissionsByTechnologyCard = 'container.report.submissionsByTechnologyCard',
	TopInterestsAsOfYesterdayCard = 'container.report.topInterestsAsOfYesterdayCard',
	TopInterestsCard = 'container.report.topInterestsCard',
	TopPagesCard = 'container.report.topPagesCard',
	ViewsByLocationCard = 'container.report.viewsByLocationCard',
	ViewsByTechnologyCard = 'container.report.viewsByTechnologyCard',
	VisitorsBehaviorCard = 'container.report.visitorsBehaviorCard',
	VisitorsByTimeCard = 'container.report.visitorsByTimeCard'
}

export const CONTAINERS: {[key in ReportContainer]: TReportContainer} = {
	[ReportContainer.AcquisitionsCard]: {
		label: Liferay.Language.get('acquisitions'),
		layout: 2
	},
	[ReportContainer.ActiveIndividualsCard]: {
		label: Liferay.Language.get('active-individuals'),
		layout: 1
	},
	[ReportContainer.AssetAppearsOnCard]: {
		label: Liferay.Language.get('asset-appears-on'),
		layout: 1
	},
	[ReportContainer.AudienceCard]: {
		label: Liferay.Language.get('audience'),
		layout: 1
	},
	[ReportContainer.CohortAnalysisCard]: {
		label: Liferay.Language.get('cohort-analysis'),
		layout: 1
	},
	[ReportContainer.CurrentTotalsCard]: {
		label: Liferay.Language.get('current-totals'),
		layout: 1
	},
	[ReportContainer.DistributionBreakdownCard]: {
		label: Liferay.Language.get('distribution-breakdown'),
		layout: 1
	},
	[ReportContainer.DownloadsByLocationCard]: {
		label: Liferay.Language.get('downloads-by-location'),
		layout: 2
	},
	[ReportContainer.DownloadsByTechnologyCard]: {
		label: Liferay.Language.get('downloads-by-technology'),
		layout: 2
	},
	[ReportContainer.EnrichedProfilesCard]: {
		label: Liferay.Language.get('enriched-profiles'),
		layout: 2
	},
	[ReportContainer.InterestsCard]: {
		label: Liferay.Language.get('interests'),
		layout: 3
	},
	[ReportContainer.SearchTermsCard]: {
		label: Liferay.Language.get('search-terms'),
		layout: 3
	},
	[ReportContainer.SegmentCompositionCard]: {
		label: Liferay.Language.get('segment-composition'),
		layout: 2
	},
	[ReportContainer.SegmentCriteriaCard]: {
		label: Liferay.Language.get('segment-criteria'),
		layout: 2
	},
	[ReportContainer.SegmentMembershipCard]: {
		label: Liferay.Language.get('segment-membership'),
		layout: 1
	},
	[ReportContainer.SessionsByLocationCard]: {
		label: Liferay.Language.get('sessions-by-location'),
		layout: 2
	},
	[ReportContainer.SessionTechnologyCard]: {
		label: Liferay.Language.get('session-technology'),
		layout: 2
	},
	[ReportContainer.SiteActivityCard]: {
		label: Liferay.Language.get('site-activity'),
		layout: 1
	},
	[ReportContainer.SubmissionsByLocationCard]: {
		label: Liferay.Language.get('submissions-by-location'),
		layout: 2
	},
	[ReportContainer.SubmissionsByTechnologyCard]: {
		label: Liferay.Language.get('submissions-by-technology'),
		layout: 2
	},
	[ReportContainer.TopInterestsCard]: {
		label: Liferay.Language.get('top-interests'),
		layout: 2
	},
	[ReportContainer.TopInterestsAsOfYesterdayCard]: {
		label: Liferay.Language.get('top-interests-as-of-yesterday'),
		layout: 1
	},
	[ReportContainer.TopPagesCard]: {
		label: Liferay.Language.get('top-pages'),
		layout: 2
	},
	[ReportContainer.ViewsByLocationCard]: {
		label: Liferay.Language.get('views-by-location'),
		layout: 2
	},
	[ReportContainer.ViewsByTechnologyCard]: {
		label: Liferay.Language.get('views-by-technology'),
		layout: 2
	},
	[ReportContainer.VisitorsBehaviorCard]: {
		label: Liferay.Language.get('visitors-behavior'),
		layout: 1
	},
	[ReportContainer.VisitorsByTimeCard]: {
		label: Liferay.Language.get('visitors-by-day-and-time'),
		layout: 3
	}
};

const PRIMARY_COLOR = '#0B5FFF';
const SECONDARY_COLOR = '#6B6C7E';
const TITLE_COLOR = '#000000';

export type TReportContainer = {label: string; layout: 1 | 2 | 3};
export type TransformedContainer = TReportContainer & {
	checked: boolean;
	id: ReportContainer;
};

export interface IDownloadReport {
	dateRangeDescription?: string;
	disabled: boolean;
	showDateRange?: boolean;
	subtitle: string;
	title: string;
	url?: string;
}

type ContainerList = {
	[key in ReportContainer]: TransformedContainer;
};

export const formattedContainers = (
	reportContainers: ReportContainer[]
): ContainerList =>
	reportContainers.reduce((acc, id) => {
		acc[id] = {
			...CONTAINERS[id],
			checked: true,
			id
		};

		return acc;
	}, {} as ContainerList);

const getContainers = async (
	containers: TransformedContainer[]
): Promise<JSPDFExtensionContainer[]> => {
	const containerArr = [];
	const promises = [];

	containers.map(({id, layout}) => {
		const containerElement = document.getElementById(id);

		if (!containerElement) {
			throw new Error(`container not found! ID: ${id}`);
		}

		const promise = html2canvas(containerElement, {
			backgroundColor: '#F1F2F5',
			logging: false
		}).then(canvas => {
			const imageData = canvas.toDataURL('image/jpeg', 1.0);

			containerArr.push({containerElement, imageData, layout});
		});

		promises.push(promise);
	});

	return Promise.all(promises).then(() => containerArr);
};

const DownloadPDFReport: React.FC<IDownloadReport> = ({
	dateRangeDescription,
	disabled,
	showDateRange,
	subtitle,
	title,
	url
}) => {
	const [loading, setLoading] = useState(false);
	const {observer, onOpenChange, open} = useModal();
	const {reportContainers} = useDownloadReportContext();
	const [containers, setContainers] = useState<ContainerList | {}>({});

	const dispatch = useDispatch();

	useEffect(() => {
		if (open) {
			setContainers(formattedContainers(reportContainers));
		}
	}, [open, reportContainers]);

	const filteredContainers = useMemo(
		() => Object.values(containers).filter(({checked}) => checked),
		[containers]
	);

	return (
		<div className='download-report'>
			<DownloadReportButton
				disabled={disabled}
				loading={loading}
				onClick={() => onOpenChange(true)}
			/>

			{open && (
				<DownloadReportModal
					dateRangeDescription={dateRangeDescription}
					disabled={!filteredContainers.length}
					infoMessage={Liferay.Language.get(
						'the-dashboard-will-be-downloaded-exactly-as-it-is-displayed-on-your-screen.-please-verify-if-the-desired-tabs-and-filters-are-selected-before-proceeding'
					)}
					observer={observer}
					onClose={() => onOpenChange(false)}
					onSubmit={() => {
						dispatch(
							addAlert({
								alertType: Alert.Types.Default,
								message: sub(
									Liferay.Language.get(
										'the-x-file-is-being-generated-and-your-download-will-start-soon'
									),
									['PDF']
								) as string
							})
						);

						setLoading(true);

						/**
						 * It is necessary to have timeout of 1000ms to wait chart
						 * animation be loaded before generate the report
						 */

						setTimeout(async () => {
							if (!containers) return;

							const doc = new JSPDFExtension({
								containers: await getContainers(
									filteredContainers
								),
								fontFamily: 'Helvetica',
								name: title
							});

							doc.addFloatText({
								color: PRIMARY_COLOR,
								posX: PosX.Right,
								posY: 10,
								size: Size.Small,
								url: window.location.href,
								value: Liferay.Language.get('access-workspace'),
								weight: Weight.Normal
							});

							doc.addText({
								color: PRIMARY_COLOR,
								size: Size.Small,
								value: 'Analytics Cloud',
								weight: Weight.Normal
							});

							doc.addText({
								color: TITLE_COLOR,
								size: Size.Medium,
								value: title,
								weight: Weight.Bold
							});

							if (url) {
								doc.addText({
									color: SECONDARY_COLOR,
									size: Size.Small,
									truncateText: true,
									url,
									value: decodeURIComponent(url),
									weight: Weight.Bold
								});
							}

							doc.addText({
								color: SECONDARY_COLOR,
								size: Size.Small,
								value: subtitle,
								weight: Weight.Normal
							});

							doc.render();

							setContainers(
								formattedContainers(reportContainers)
							);

							setLoading(false);
						}, 1000);
					}}
					showDateRange={showDateRange}
				>
					<ClayForm.Group className='mt-3'>
						<label>
							<Text size={3}>
								{Liferay.Language.get('dashboard-reports')}
							</Text>
						</label>

						<p>
							<Text size={3}>
								{Liferay.Language.get(
									'select-the-reports-to-be-exported-as-a-single-PDF-file'
								)}
							</Text>
						</p>

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
	const [checked, setChecked] = useState(true);

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
