import CodeSnippet from 'shared/components/CodeSnippet';
import React from 'react';

class CodeSnippetKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<CodeSnippet code='Test code snippet' />
			</div>
		);
	}
}

export default CodeSnippetKit;
