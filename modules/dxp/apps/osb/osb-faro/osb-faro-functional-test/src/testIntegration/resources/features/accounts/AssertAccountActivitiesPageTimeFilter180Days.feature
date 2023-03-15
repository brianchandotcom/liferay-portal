@spira_Accounts @Accounts @team_FARO @priority_3
Feature: Assert 180 day time filter in Account Activities
	As an Business User, I should be able to change the time filter to 180 days in Account Activities

	Background: [Setup] Navigate to Accounts
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Accounts" page
		* I click "Hilll, Gottlieb and Dicki" in the table

	Scenario: Change time filter to 180 days in Account Activities
		Given I click the "Activities" tab
		And I click the "Last 30 days" dropdown in the card
		When I click the "More Preset Periods" button
		And I click the "Last 180 days" button
		Then I should see time filter "Last 180 days" in card