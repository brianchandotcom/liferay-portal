import ClayButton from '@clayui/button';
import Modal from 'shared/components/modal';
import React from 'react';
import {Text} from '@clayui/core';

interface IReadyToGoProps {
	onClose: () => void;
}

const ReadyToGo: React.FC<IReadyToGoProps> = ({onClose}) => (
	<>
		<Modal.Header onClose={onClose} />

		<Modal.Body>
			{/* TODO: LRAC-7427 Adjust SVGs with Linear Gradients */}
			<div className='icon analytics-onboarding-ready-to-use' />

			<div className='text-center mb-4'>
				<Text size={10} weight='bold'>
					{Liferay.Language.get('you-are-ready-to-go')}
				</Text>

				<p>
					<Text color='secondary' size={6}>
						{Liferay.Language.get('your-workspace-is-all-set-up')}
					</Text>
				</p>
			</div>

			<div className='text-center'>
				<p>
					<Text color='secondary'>
						{Liferay.Language.get(
							'tracking-will-start-immediately-however-it-may-take-some-time-for-data-to-appear-in-your-workspace'
						)}
					</Text>
				</p>

				<p>
					<Text color='secondary'>
						{Liferay.Language.get(
							'make-sure-to-set-your-time-period-to-last-24-hours-to-see-if-your-data-is-coming-in-correctly'
						)}
					</Text>
				</p>
			</div>
		</Modal.Body>

		<Modal.Footer className='d-flex justify-content-end'>
			<ClayButton
				className='button-root'
				displayType='primary'
				onClick={onClose}
			>
				{Liferay.Language.get('done')}
			</ClayButton>
		</Modal.Footer>
	</>
);

export default ReadyToGo;
