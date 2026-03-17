config {
  call_module_type = "all"
  force  = false
}
plugin "google" {
  enabled = true
  source  = "github.com/terraform-linters/tflint-ruleset-google"
  version = "0.38.0"
}
plugin "terraform" {
  enabled = true
  preset  = "recommended"
}