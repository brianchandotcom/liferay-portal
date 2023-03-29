@spira_Pagination @Pagination @Accounts @List @team_FARO @priority_3
Feature: Account interest details has pagination
	As a Business User, I should be able to paginate to the second page of account interest details

	Background: [Setup] Navigate to the Accounts page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Accounts" page

	Scenario: Paginate to the second page of account interest details
		Given I click "Schneider and Sons" in the table
		And I click the "Interests" tab
		And I click "rich e-commerce" in the table
		And I set the pagination delta to "5"
		When I go to page "2" in the table
		Then I should be on page "2" in the table
		And I should see an individual named "Sharla Witting" in the table