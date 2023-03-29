@spira_Login @Login @team_FARO @priority_5
Feature: Sign out of AC
	As a Business User, I should be able to sign out of AC and view login screen

	Background: [Setup] Navigate to Home page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I click the "test test" button
		* I click the "Sign Out" User Menu option

	Scenario: Sign out on workspace select page
		Given I login as "test@faro.io:test" not selecting workspace
		And I click the name of the signed in user
		When I click the "Sign Out" User Menu option
		Then I should see the "Sign-in" page