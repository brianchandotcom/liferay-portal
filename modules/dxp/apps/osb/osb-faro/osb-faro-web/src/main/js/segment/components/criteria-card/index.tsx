import Card from 'shared/components/Card';
import ClayButton from '@clayui/button';
import CriteriaView from './CriteriaView';
import getCN from 'classnames';
import Label from 'shared/components/Label';
import React, {useState} from 'react';
import {ReportContainer} from 'shared/components/download-report/DownloadPDFReport';
import {Segment} from 'shared/util/records';
import {SegmentTypes} from 'shared/util/constants';
import {translateQueryToCriteria} from 'segment/segment-editor/dynamic/utils/odata';
import {withReferencedObjectsProvider} from 'segment/segment-editor/dynamic/context/referencedObjects';

const HEADER_MARGIN = 16;

interface ICriteriaCardProps {
	criteriaString: string;
	includeAnonymousUsers: boolean;
	segment: Segment;
	timeZoneId: string;
}

interface ICriteriaCardState {
	expand: boolean;
	truncate: boolean;
}

const CriteriaCard: React.FC<ICriteriaCardProps> = ({
	criteriaString,
	includeAnonymousUsers,
	segment,
	timeZoneId
}) => {
	const _criteriaViewRef = React.createRef<HTMLDivElement>();

	const [cardState, setCardState] = useState<ICriteriaCardState>({
		expand: false,
		truncate: true
	});

	const hideOverflow = !cardState.expand && cardState.truncate;

	const handleClick = () => {
		setCardState({...cardState, expand: true});
	};

	const updateCriteriaTruncation = () => {
		const node = _criteriaViewRef.current;

		if (node) {
			const {bottom} = node.getBoundingClientRect();

			setCardState({
				...cardState,
				truncate: bottom > window.innerHeight - HEADER_MARGIN
			});
		}
	};

	window.addEventListener('resize', updateCriteriaTruncation);

	return (
		<Card
			className='criteria-card-root'
			reportContainer={
				segment.segmentType === SegmentTypes.Batch &&
				ReportContainer.SegmentCriteriaCard
			}
		>
			<Card.Header>
				<Card.Title>
					{Liferay.Language.get('segment-criteria')}
				</Card.Title>

				{includeAnonymousUsers && (
					<Label display='secondary' size='lg' uppercase>
						{Liferay.Language.get('include-anonymous')}
					</Label>
				)}
			</Card.Header>

			<Card.Body className={getCN({truncate: hideOverflow})}>
				<CriteriaView
					criteria={translateQueryToCriteria(criteriaString)}
					ref={_criteriaViewRef}
					timeZoneId={timeZoneId}
				/>
			</Card.Body>

			{hideOverflow && (
				<div className='fade-out-cover'>
					<div className='view-all-button-container'>
						<ClayButton
							className='button-root'
							displayType='unstyled'
							onClick={handleClick}
							size='sm'
						>
							{Liferay.Language.get('view-all-criteria')}
						</ClayButton>
					</div>
				</div>
			)}
		</Card>
	);
};

export default withReferencedObjectsProvider(CriteriaCard);
