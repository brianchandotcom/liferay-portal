@spira_Pagination @Pagination @Accounts @List @team_FARO @priority_3
Feature: Account interests list has pagination
	As a Business User, I should be able to paginate to the second page of account interests

	Background: [Setup] Navigate to Accounts page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Accounts" page

	Scenario: Paginate to second page of account interests
		Given I click "Schneider and Sons" in the table
		And I click the "Interests" tab
		And I set the pagination delta to "5"
		When I go to page "2" in the table
		Then I should be on page "2" in the table
		And I should see an interest named "visionary platforms" in the table