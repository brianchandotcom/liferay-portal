import Button from 'shared/components/Button';
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
			<Button onClick={() => onClose}>{'Cancel'}</Button>

			<Button>{'Submit'}</Button>
		</Modal.Footer>
	</Modal>
);

export default TestModal;
