import Button from 'shared/components/Button';
import getCN from 'classnames';
import NoResultsDisplay from 'shared/components/NoResultsDisplay';
import React, {FC} from 'react';

interface IErrorDisplayProps extends React.HTMLAttributes<HTMLElement> {
	buttonLabel?: string;
	message?: string;
	onReload?: () => void;
	spacer?: boolean;
}

const ErrorDisplay: FC<IErrorDisplayProps> = ({
	buttonLabel = Liferay.Language.get('reload'),
	className,
	message = Liferay.Language.get('an-unexpected-error-occurred'),
	onReload,
	spacer = false
}) => (
	<NoResultsDisplay
		className={getCN(
			'error-display-root',
			'flex-grow-1',
			{'error-spacer': spacer},
			className
		)}
		title={message}
	>
		{onReload && <Button onClick={() => onReload()}>{buttonLabel}</Button>}
	</NoResultsDisplay>
);

export default ErrorDisplay;
