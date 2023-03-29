@spira_Accounts @Accounts @team_FARO @priority_4
Feature: Assert Engagement Score time filter is disabled for Account Activities
	As an Business User, I should see that the Engagement Score card's time filters are disabled for Account Activities

	Background: [Setup] Navigate to Accounts
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Accounts" page
		* I click "Hilll, Gottlieb and Dicki" in the table

	Scenario: Assert Engagement Score time filter is disabled for Account Activities
		Given I click the "Activities" tab
		When I click the "Engagement Score" card tab
		Then I should see the time filter is disabled