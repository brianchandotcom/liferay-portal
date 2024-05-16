import BaseScreen from './BaseScreen';
import ClayButton from '@clayui/button';
import Modal from 'shared/components/modal';
import React from 'react';

interface IReadyToGoProps {
	onClose: () => void;
}

const ReadyToGo: React.FC<IReadyToGoProps> = ({onClose}) => (
	<BaseScreen className='ready-to-go' onClose={onClose}>
		<Modal.Body className='d-flex flex-column align-items-center'>
			{/* TODO: LRAC-7427 Adjust SVGs with Linear Gradients */}
			<div className='ac-ready-to-use' />

			<span className='title d-flex justify-content-center'>
				{Liferay.Language.get('you-are-ready-to-go')}
			</span>

			<div className='d-flex description flex-column text-center'>
				<h4 className='pb-3'>
					{Liferay.Language.get('your-workspace-is-all-set-up')}
				</h4>

				<h4 className='pb-3'>
					{Liferay.Language.get(
						'tracking-will-start-immediately-however-it-may-take-some-time-for-data-to-appear-in-your-workspace'
					)}
				</h4>

				<h4>
					{Liferay.Language.get(
						'make-sure-to-set-your-time-period-to-last-24-hours-to-see-if-your-data-is-coming-in-correctly'
					)}
				</h4>
			</div>
		</Modal.Body>

		<Modal.Footer className='d-flex justify-content-end'>
			<ClayButton
				className='button-root'
				displayType='primary'
				onClick={onClose}
			>
				{Liferay.Language.get('next')}
			</ClayButton>
		</Modal.Footer>
	</BaseScreen>
);

export default ReadyToGo;
