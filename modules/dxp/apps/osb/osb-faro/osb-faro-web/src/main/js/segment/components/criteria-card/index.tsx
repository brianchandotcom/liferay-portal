import CriteriaView from './CriteriaView';
import Label from 'shared/components/Label';
import Panel from '@clayui/panel';
import React, {useEffect} from 'react';
import {ReportContainer} from 'shared/components/download-report/DownloadPDFReport';
import {SegmentTypes} from 'shared/util/constants';
import {translateQueryToCriteria} from 'segment/segment-editor/dynamic/utils/odata';
import {useDownloadReportContext} from 'shared/components/download-report/DownloadReportContext';

interface ICriteriaCardProps {
	criteriaString: string;
	includeAnonymousUsers: boolean;
	segmentType: SegmentTypes;
	timeZoneId: string;
}

const CriteriaCard: React.FC<ICriteriaCardProps> = ({
	criteriaString,
	includeAnonymousUsers,
	segmentType,
	timeZoneId
}) => {
	const _criteriaViewRef = React.createRef<HTMLDivElement>();

	const {
		clearReportContainers,
		setReportContainer
	} = useDownloadReportContext();

	useEffect(() => {
		setReportContainer(ReportContainer.SegmentCriteriaCard);

		return clearReportContainers;
	}, []);

	return (
		<Panel
			className='card-root'
			collapsable
			defaultExpanded
			displayTitle={
				<Panel.Title className='card-title'>
					{Liferay.Language.get('segment-criteria')}
				</Panel.Title>
			}
			id={ReportContainer.SegmentCriteriaCard}
		>
			<Panel.Body className='criteria-card-root'>
				{includeAnonymousUsers && (
					<Label display='info' size='lg' uppercase>
						{Liferay.Language.get('includes-anonymous-individuals')}
					</Label>
				)}
				<CriteriaView
					criteria={translateQueryToCriteria(criteriaString)}
					ref={_criteriaViewRef}
					segmentType={segmentType}
					timeZoneId={timeZoneId}
				/>
			</Panel.Body>
		</Panel>
	);
};

export default CriteriaCard;
