import ClayButton from '@clayui/button';
import ClayLink from '@clayui/link';
import React from 'react';
import TitleEditor from 'shared/components/TitleEditor';
import {Routes, toRoute} from 'shared/util/router';
import {useParams} from 'react-router-dom';

interface IEventAnalysisToolbarProps extends React.HTMLAttributes<HTMLElement> {
	isValid: boolean;
}

const EventAnalysisToolbar: React.FC<IEventAnalysisToolbarProps> = ({
	isValid
}) => {
	const {channelId, groupId} = useParams();

	return (
		<div className='event-analysis-toolbar-root'>
			<div className='event-analysis-toolbar-left-content'>
				<TitleEditor
					name='name'
					placeholder={Liferay.Language.get('unnamed-analysis')}
				/>
			</div>

			<div className='event-analysis-toolbar-right-content'>
				<ClayButton
					className='button-root mr-2'
					disabled={!isValid}
					displayType='primary'
					size='sm'
					type='submit'
				>
					{Liferay.Language.get('save-analysis')}
				</ClayButton>

				<ClayLink
					button
					className='button-root'
					displayType='secondary'
					href={toRoute(Routes.EVENT_ANALYSIS, {
						channelId,
						groupId
					})}
					small
				>
					{Liferay.Language.get('cancel')}
				</ClayLink>
			</div>
		</div>
	);
};

export default EventAnalysisToolbar;
