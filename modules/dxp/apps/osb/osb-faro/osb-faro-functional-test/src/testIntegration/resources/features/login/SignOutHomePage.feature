@spira_Login @Login @team_FARO @priority_5
Feature: Sign out of AC
	As a Business User, I should be able to sign out of AC and view login screen

	Background: [Setup] Navigate to Home page
		* I go to the "Home" page

	Scenario: Sign out on workspace home page
		Given I login as "test@faro.io:test"
		And I should see the "Sites" page
		And I click the "test test" button
		When I click the "Sign Out" User Menu option
		Then I should see the "Sign-in" page