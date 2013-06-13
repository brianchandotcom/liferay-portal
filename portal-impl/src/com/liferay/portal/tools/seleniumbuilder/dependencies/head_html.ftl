<head>
	<script type='text/javascript'>
		function takeAction(e) {
			var node = e.srcElement == undefined ? e.target : e.srcElement;

			var id = node.getAttribute("id");

			if (id != null) {
				if (node.innerHTML == "-") {
					node.innerHTML = "+";

					document.getElementById("Collapse" + id).style.display = "none";
				} else if (node.innerHTML == "+") {
					node.innerHTML = "-";

					document.getElementById("Collapse" + id).style.display = "block";
				}
			}
		}
	</script>

	<style>
		body {
			font-family: verdana;
			font-size: 12px;
			line-height: 1.75em;
		}

		#log {
			height: 250px;
			width: 500px;
			border: 1px solid #ccc;
			overflow: auto;
		}

		.arrow {
			color: blue;
		}

		.attribute {
			color: purple;
		}

		.fail {
			background-color: #FF8B8B;
		}

		.pass {
			background-color: #B5FF8B;
		}

		.pending {
			background-color: #FBFF8B;
		}

		.quote {
			color: DeepPink;
		}

		.tag {
			color: green;
		}

		li {
			display: block;
		}

		.expandToggle {
			float: left;
			margin-right: 5px;
			width: 8px;
			cursor: pointer;
		}

		.expandLine {
			font-weight: bold;
			cursor: pointer;
		}

		.closingTag {
			display: none;
		}

		.collapse {
			display: none;
		}

		ul {
			list-style-type: none;
		}
	</style>
</head>