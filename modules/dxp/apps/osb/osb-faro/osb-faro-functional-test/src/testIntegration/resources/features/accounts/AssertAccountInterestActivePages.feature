@spira_Accounts @Accounts @team_FARO @priority_3
Feature: Interest details has a tab that shows active pages
	As a Business User, I should be able to see a tab with active pages

	Background: [Setup] Navigate to Accounts
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Accounts" page
		* I click "Schneider and Sons" in the table

	Scenario: Active pages shown for segment
		Given I click the "Interests" tab
		And I click "rich e-commerce" in the table
		When I click the "Active Pages" tab
		Then I should see page named "orchestrate rich e-commerce" in the table