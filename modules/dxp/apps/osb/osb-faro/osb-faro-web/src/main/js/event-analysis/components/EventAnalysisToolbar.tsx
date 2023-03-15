import Button from 'shared/components/Button';
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
				<Button.Group>
					<Button.GroupItem>
						<Button
							disabled={!isValid}
							display='primary'
							size='sm'
							type='submit'
						>
							{Liferay.Language.get('save-analysis')}
						</Button>
					</Button.GroupItem>

					<Button.GroupItem>
						<Button
							href={toRoute(Routes.EVENT_ANALYSIS, {
								channelId,
								groupId
							})}
							size='sm'
						>
							{Liferay.Language.get('cancel')}
						</Button>
					</Button.GroupItem>
				</Button.Group>
			</div>
		</div>
	);
};

export default EventAnalysisToolbar;
