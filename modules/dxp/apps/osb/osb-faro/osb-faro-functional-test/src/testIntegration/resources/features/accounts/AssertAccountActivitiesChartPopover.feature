@spira_Accounts @Accounts @team_FARO @priority_3
Feature: Assert Account Activities Chart Popover format
	As an Business User, I should see that the Account Activities chart popover follows the correct format

	Background: [Setup] Navigate to Accounts
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Accounts" page
		* I click "Hilll, Gottlieb and Dicki" in the table

	Scenario: Assert Account Activities chart popover format
		Given I click the "Activities" tab
		When I mouse over row "1" in "Account Activities" card
		Then I should see chart popover formatted YYYY MMM DD