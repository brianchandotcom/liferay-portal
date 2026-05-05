config {
	call_module_type="all"
	force=false
}
plugin "aws" {
	enabled=true
	source="github.com/terraform-linters/tflint-ruleset-aws"
	version="0.47.0"
}
plugin "terraform" {
	enabled=true
	preset="recommended"
}