data "aws_availability_zones" "available" {
	filter {
		name="opt-in-status"
		values=["opt-in-not-required"]
	}
}
data "aws_caller_identity" "current" {
}