import classNames from 'classnames';

const Footer = ({leftButton, middleButton, rightButton}) => {
	const isCornerButton = leftButton || rightButton;

	return (
		<div
			className={classNames('d-flex', 'p-4', {
				'justify-content-between': isCornerButton,
				'justify-content-center': !isCornerButton,
			})}
		>
			{leftButton}
			{middleButton}
			{rightButton}
		</div>
	);
};

export default Footer;
