import React from 'react';
import TextTruncate from 'shared/components/TextTruncate';

class TextTruncateKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<div style={{width: '150px'}}>
					<TextTruncate title='Visible text and tooltip will be the same.' />
				</div>

				<div style={{width: '150px'}}>
					<TextTruncate title='This has specific tooltip content.'>
						{'Here is the long visible text'}
					</TextTruncate>
				</div>

				<div style={{width: '300px'}}>
					<TextTruncate title='No tooltip will show for untruncated text.' />
				</div>

				<TextTruncate title='Tooltip will update when the window is resized. Tooltip will update when the window is resized.' />
			</div>
		);
	}
}

export default TextTruncateKit;
