import CriteriaView from './CriteriaView';
import Label from 'shared/components/Label';
import Panel from '@clayui/panel';
import React from 'react';
import {translateQueryToCriteria} from 'segment/segment-editor/dynamic/utils/odata';

interface ICriteriaCardProps {
	criteriaString: string;
	includeAnonymousUsers: boolean;
	timeZoneId: string;
}

const CriteriaCard: React.FC<ICriteriaCardProps> = ({
	criteriaString,
	includeAnonymousUsers,
	timeZoneId
}) => {
	const _criteriaViewRef = React.createRef<HTMLDivElement>();

	return (
		<Panel
			className='card-root p-3'
			collapsable
			defaultExpanded
			displayTitle={
				<Panel.Title className='card-title'>
					{Liferay.Language.get('segment-criteria')}
				</Panel.Title>
			}
		>
			<Panel.Body className='criteria-card-root pt-3'>
				{includeAnonymousUsers && (
					<Label display='info' size='lg' uppercase>
						{Liferay.Language.get('includes-anonymous-individuals')}
					</Label>
				)}
				<CriteriaView
					criteria={translateQueryToCriteria(criteriaString)}
					ref={_criteriaViewRef}
					timeZoneId={timeZoneId}
				/>
			</Panel.Body>
		</Panel>
	);
};

export default CriteriaCard;
