import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import Clipboard from 'clipboard';
import React, {useEffect} from 'react';
import type {DisplayType} from '@clayui/button/lib/Button';

interface ICopyButtonProps {
	buttonText?: string;
	className?: string;
	displayType?: DisplayType;
	onClick?: (any) => void;
	position?: string;
	text: string;
}

const CopyButton: React.FC<ICopyButtonProps> = ({
	buttonText,
	displayType,
	onClick,
	text,
	...otherProps
}) => {
	useEffect(() => {
		const _clipboard = new Clipboard('[data-clipboard-text]');

		return () => _clipboard.destroy();
	}, []);

	return (
		<ClayButton
			aria-label={Liferay.Language.get('click-to-copy')}
			className='button-root'
			data-clipboard-text={text}
			data-tooltip-response={Liferay.Language.get('copied')}
			displayType={displayType}
			onClick={onClick}
			title={Liferay.Language.get('click-to-copy')}
			{...otherProps}
		>
			{buttonText || <ClayIcon className='icon-root' symbol='copy' />}
		</ClayButton>
	);
};

export default CopyButton;
