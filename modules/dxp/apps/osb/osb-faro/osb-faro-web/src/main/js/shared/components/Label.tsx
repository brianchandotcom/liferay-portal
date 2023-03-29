import Button from 'shared/components/Button';
import getCN from 'classnames';
import omitDefinedProps from 'shared/util/omitDefinedProps';
import React, {FC} from 'react';

enum Displays {
	Primary = 'primary',
	Secondary = 'secondary',
	Success = 'success',
	Info = 'info',
	Warning = 'warning',
	Danger = 'danger',
	Light = 'light',
	Dark = 'dark'
}

enum Sizes {
	Large = 'lg'
}

const Label: FC<{
	className?: string;
	display?: string;
	index?: number;
	onRemove?: (index?: number) => void;
	size?: string;
	uppercase?: boolean;
}> & {Displays: typeof Displays; Sizes: typeof Sizes} = props => {
	const {
		children,
		className,
		display,
		index,
		onRemove,
		size,
		uppercase = false,
		...otherProps
	} = props;

	const handleRemove = () => {
		onRemove && onRemove(index);
	};

	const classes = getCN('label', 'label-root', className, {
		[`label-${display}`]: display,
		[`label-${size}`]: size,
		'label-dismissible': onRemove,
		['label-uppercase']: uppercase
	});

	return (
		<div {...omitDefinedProps(otherProps, props)} className={classes}>
			<span className='label-item'>{children}</span>

			{onRemove && (
				<span className='label-item label-item-after'>
					<Button
						aria-label={Liferay.Language.get('close')}
						className='close'
						icon='times'
						iconAlignment='right'
						onClick={handleRemove}
					/>
				</span>
			)}
		</div>
	);
};

Label.Displays = Displays;
Label.Sizes = Sizes;

export default Label;
