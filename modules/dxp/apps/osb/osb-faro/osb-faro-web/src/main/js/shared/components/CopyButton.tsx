import Button, {Displays} from 'shared/components/Button';
import Clipboard from 'clipboard';
import Icon from 'shared/components/Icon';
import React, {useEffect} from 'react';

interface ICopyButtonProps {
	buttonText?: string;
	className?: string;
	display?: Displays;
	onClick?: (any) => void;
	position?: string;
	text: string;
}

const CopyButton: React.FC<ICopyButtonProps> = ({
	buttonText,
	display,
	onClick,
	text,
	...otherProps
}) => {
	useEffect(() => {
		const _clipboard = new Clipboard('[data-clipboard-text]');

		return () => _clipboard.destroy();
	}, []);

	return (
		<Button
			aria-label={Liferay.Language.get('click-to-copy')}
			data-clipboard-text={text}
			data-tooltip-response={Liferay.Language.get('copied')}
			display={display}
			onClick={onClick}
			title={Liferay.Language.get('click-to-copy')}
			{...otherProps}
		>
			{buttonText || <Icon symbol='paste' />}
		</Button>
	);
};

export default CopyButton;
