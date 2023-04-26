import ClayButton from '@clayui/button';
import getCN from 'classnames';
import Input from 'shared/components/Input';
import InputList from 'shared/components/InputList';
import Modal from 'shared/components/modal';
import React, {useState} from 'react';
import {COMMA, ENTER} from 'shared/util/key-constants';

//	regex to validate words with accents and avoid special characters
const KEYWORD_VALIDATOR_REGEX = /[\w\u00C0-\u00ff]+/;

interface IInterestTopicsModalProps {
	className: string;
	onClose: () => void;
	onSubmit: (keywords: string[]) => void;
}

const InterestTopicsModal: React.FC<IInterestTopicsModalProps> = ({
	className,
	onClose,
	onSubmit,
	...otherProps
}) => {
	const [inputValue, setInputValue] = useState('');
	const [keywords, setKeywords] = useState<string[]>([]);

	const handleSubmit = () => {
		if (
			(keywords.length && !inputValue) ||
			(inputValue && validateKeyword(inputValue))
		) {
			onSubmit(keywords);
		}
	};

	const validateKeyword = (value: string): boolean =>
		KEYWORD_VALIDATOR_REGEX.test(value);

	return (
		<Modal
			{...otherProps}
			className={getCN('invite-users-modal-root', className)}
			size='lg'
		>
			<Modal.Header
				onClose={onClose}
				title={Liferay.Language.get('insert-keywords')}
			/>

			<Modal.Body>
				<Input.Group>
					<InputList
						errorMessage={Liferay.Language.get(
							'please-enter-a-valid-keyword'
						)}
						inputValue={inputValue}
						items={keywords}
						keyCodesToSplit={[COMMA, ENTER]}
						onInputChange={(value: string) => {
							setInputValue(value.toLowerCase());
						}}
						onItemsChange={setKeywords}
						placeholder={Liferay.Language.get('enter-keyword')}
						validateOnBlur
						validationFn={validateKeyword}
					/>
				</Input.Group>
				<div className='description form-text'>
					{Liferay.Language.get(
						'use-comma-or-enter-to-add-several-keywords'
					)}
				</div>
			</Modal.Body>

			<Modal.Footer>
				<ClayButton
					className='button-root'
					displayType='secondary'
					onClick={onClose}
				>
					{Liferay.Language.get('cancel')}
				</ClayButton>

				<ClayButton
					className='button-root'
					disabled={!inputValue && !keywords.length}
					displayType='primary'
					onClick={handleSubmit}
				>
					{Liferay.Language.get('send')}
				</ClayButton>
			</Modal.Footer>
		</Modal>
	);
};

export default InterestTopicsModal;
