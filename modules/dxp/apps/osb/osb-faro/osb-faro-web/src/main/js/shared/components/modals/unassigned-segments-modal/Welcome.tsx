import ClayButton from '@clayui/button';
import Modal from 'shared/components/modal';
import React from 'react';

interface IWelcomeProps {
	groupId?: string;
	onNext: (increment?: number) => void;
}

const Welcome: React.FC<IWelcomeProps> = ({onNext}) => (
	<div className='welcome'>
		<Modal.Body className='d-flex flex-column align-items-center'>
			{/* TODO: LRAC-7427 Adjust SVGs with Linear Gradients */}
			<div className='ac-property-buildings' />

			<h2>{Liferay.Language.get('introducing-properties')}</h2>

			<span className='subtitle'>
				{Liferay.Language.get(
					'we-ve-upgraded-your-analytics-cloud-workspace-to-include-properties'
				)}
			</span>

			<ul className='description'>
				<li>
					<p className='description-title'>
						{Liferay.Language.get(
							'track-user-activities-across-experiences-that-matter'
						)}
					</p>
					<p className='description-subtitle'>
						{Liferay.Language.get(
							'scope-user-sessions-to-their-intended-experiences-by-combining-sites-for-each-property'
						)}
					</p>
					<p>
						{Liferay.Language.get(
							'for-existing-workspaces-we-scoped-each-datasource-to-its-own-property-this-may-have-affected-some-of-your-existing-segments'
						)}
					</p>
				</li>
				<li>
					<p className='description-title'>
						{Liferay.Language.get(
							'take-control-of-the-data-access-with-property-permissions'
						)}
					</p>
					<p className='description-subtitle'>
						{Liferay.Language.get(
							'flexible-user-access-controls-with-custom-permissioning-for-each-property'
						)}
					</p>
				</li>
			</ul>
		</Modal.Body>

		<Modal.Footer className='d-flex justify-content-end'>
			<ClayButton
				autoFocus
				className='button-root wide'
				displayType='secondary'
				onClick={() => onNext()}
			>
				{Liferay.Language.get('next')}
			</ClayButton>
		</Modal.Footer>
	</div>
);

export default Welcome;
