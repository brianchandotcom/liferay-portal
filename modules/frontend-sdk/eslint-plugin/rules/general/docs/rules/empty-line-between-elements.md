# Enforce an empty line between sibling elements (empty-line-between-elements)

For uniformity, and visibility, we use this rule to enforce an empty line between sibling elements.

## Rule Details

Examples of **incorrect** code for this rule:

```jsx
<div>
	<span>{'foo'}</span>
	<div>{'bar'}</div>
</div>
```

Examples of **correct** code for this rule:

```jsx
<div>
	<span>{'foo'}</span>

	<div>{'bar'}</div>
</div>
```
