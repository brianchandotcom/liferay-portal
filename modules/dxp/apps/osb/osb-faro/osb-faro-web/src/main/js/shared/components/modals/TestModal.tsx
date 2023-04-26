import ClayButton from '@clayui/button';
import Modal from 'shared/components/modal';
import React from 'react';
import {noop} from 'lodash';

const TestModal: React.FC<{onClose: () => void; title: string}> = ({
	onClose = noop,
	title = 'Modal'
}) => (
	<Modal>
		<Modal.Header onClose={() => onClose} title={title} />

		<Modal.Body inlineScroller>
			<h4>{'Modal Body'}</h4>
		</Modal.Body>

		<Modal.Footer>
			<ClayButton
				className='button-root'
				displayType='secondary'
				onClick={() => onClose}
			>
				{'Cancel'}
			</ClayButton>

			<ClayButton className='button-root' displayType='primary'>
				{'Submit'}
			</ClayButton>
		</Modal.Footer>
	</Modal>
);

export default TestModal;
