@spira_Accounts @Accounts @team_FARO @priority_3
Feature: Selected account activity point can clear date selection
	As a Business User, I should be able to clear date selection from account activities

	Background: [Setup] Navigate to Accounts
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Accounts" page
		* I click "Schowalter-Wisozk" in the table

	Scenario: Select clear date selection
		Given I click the "Activities" tab
		And I click row number "1" in the bar graph table
		When I click the "Clear Date Selection" button
		Then I should see an element exists in the table