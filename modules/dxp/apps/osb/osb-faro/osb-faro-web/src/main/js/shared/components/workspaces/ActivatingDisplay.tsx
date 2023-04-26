import ClayLink from '@clayui/link';
import React from 'react';
import Sheet from 'shared/components/Sheet';
import URLConstants from 'shared/util/url-constants';
import WorkspacesBasePage from 'shared/components/workspaces/BasePage';
import {fetchProject} from 'shared/actions/projects';
import {ProjectStates} from 'shared/util/constants';
import {withPolling} from 'shared/hoc';

const ActivatingDisplay = () => (
	<WorkspacesBasePage title={Liferay.Language.get('activating-workspace')}>
		<Sheet>
			<Sheet.Header className='mb-4'>
				<h3 className='title'>
					{Liferay.Language.get('your-workspace-is-being-activated')}
				</h3>
			</Sheet.Header>
			<Sheet.Body>
				<Sheet.Section>
					<p>
						{Liferay.Language.get(
							'this-process-will-take-a-couple-minutes'
						)}
					</p>
					<p>
						{Liferay.Language.get(
							'in-the-meantime-check-out-our-documentation-to-get-familiar-with-the-features'
						)}
					</p>
				</Sheet.Section>
			</Sheet.Body>

			<Sheet.Footer divider={false}>
				<ClayLink
					button
					className='button-root'
					displayType='primary'
					href={URLConstants.DocumentationAdminLink}
					target='_blank'
				>
					{Liferay.Language.get('check-out-docs')}
				</ClayLink>
			</Sheet.Footer>
		</Sheet>
	</WorkspacesBasePage>
);

export default withPolling(
	fetchProject,
	({state}) => state !== ProjectStates.Activating
)(ActivatingDisplay);
