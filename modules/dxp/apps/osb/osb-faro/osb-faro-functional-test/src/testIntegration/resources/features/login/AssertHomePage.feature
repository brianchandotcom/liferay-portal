@spira_Login @Login @team_FARO @priority_5
Feature: Assert that the Site Report is the AC home page upon login
	As an Business User, I should see that that Site Report page is the home page of AC upon login

	Scenario: Assert that the Site Report is the AC home page upon login
		Given I go to the "Home" page
		When I login as "test@faro.io:test"
		Then I should see the "Sites" page